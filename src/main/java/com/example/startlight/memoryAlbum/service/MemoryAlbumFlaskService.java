package com.example.startlight.memoryAlbum.service;

import com.example.startlight.memoryAlbum.dao.MemoryAlbumDao;
import com.example.startlight.memoryAlbum.dto.LetterGenerateWithFileDto;
import com.example.startlight.memoryAlbum.dto.LetterGeneratedRepDto;
import com.example.startlight.pet.entity.Personality;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemoryAlbumFlaskService {
    @Value("${ml.api}")
    public String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final MemoryAlbumDao memoryAlbumDao;
    private final MemoryAlbumService memoryAlbumService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 파싱용

    private boolean trainingInProgress = false;
    private ScheduledExecutorService executor;

    private Long trainingPetId;

    @PostConstruct
    public void init() {
        executor = Executors.newSingleThreadScheduledExecutor(); // 스케줄러 초기화
    }

    public void generateMemoryAlbum(Long petId) {
        if (trainingInProgress) {
            System.out.println("Training is already in progress");
            return;
        }

        trainingPetId = petId;

//        List<String> images = List.of(
//                "https://starlightbucket.s3.amazonaws.com/ml_test/kkong1.jpg",
//                "https://starlightbucket.s3.amazonaws.com/ml_test/kkong2.jpg",
//                "https://starlightbucket.s3.amazonaws.com/ml_test/kkong3.jpg",
//                "https://starlightbucket.s3.amazonaws.com/ml_test/kkong4.jpg",
//                "https://starlightbucket.s3.amazonaws.com/ml_test/kkong5.jpg"
//        );

        List<String> images = memoryAlbumDao.getRecent5ImgsByPetId(petId);
        System.out.println(images);

        Map<String, Object> requestBody = Map.of("images", images);

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl + "/letter_train", requestBody, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Training started...");
            trainingInProgress = true;
            // ✅ training 시작 시 10초마다 상태 체크 실행
            executor.scheduleAtFixedRate(this::checkTrainingStatus, 0, 10, TimeUnit.SECONDS);
        } else {
            throw new RuntimeException("Failed to start training: " + response.getStatusCode());
        }
    }

    public void checkTrainingStatus() {
        if (!trainingInProgress) {
            return;
        }

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl + "/training_status", String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                System.out.printf("[%s] Training status: %s\n", LocalDateTime.now(), responseBody);

                // ✅ JSON에서 상태 값 추출
                String status = objectMapper.readTree(responseBody).get("status").asText();

                if ("completed".equalsIgnoreCase(status)) {
                    System.out.println("Training finished with status: " + status);

                    // ✅ training 완료 → letter 생성 실행
                    LetterGeneratedRepDto letterGeneratedRepDto = letterGenerate(trainingPetId);
                    System.out.println("Generated Letter: " + letterGeneratedRepDto.toString());
                    memoryAlbumService.createMemoryAlbum(trainingPetId, letterGeneratedRepDto);
                    // ✅ 상태 완료 후 스케줄 중지
                    stopTraining();
                } else if ("failed".equalsIgnoreCase(status)) {
                    System.out.println("Training failed with status: " + status);

                    // ✅ 실패 시 반복 중지
                    stopTraining();
                }
            }
        } catch (Exception e) {
            System.out.println("Error while checking training status: " + e.getMessage());
            stopTraining(); // 예외 발생 시 중지
        }
    }

    public LetterGeneratedRepDto letterGenerate(Long petId) throws JsonProcessingException {

        LetterGenerateWithFileDto letterGenerateWithFileDto = memoryAlbumService.generateDto(petId);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // 요청 엔터티 생성
        HttpEntity<LetterGenerateWithFileDto> requestEntity = new HttpEntity<>(letterGenerateWithFileDto, headers);

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl + "/letter_generate",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Response: " + response.getBody());

            // 🔹 JSON 파싱 → DTO 변환
            LetterGeneratedRepDto dto = objectMapper.readValue(response.getBody(), LetterGeneratedRepDto.class);

            System.out.println("✅ Images: " + dto.getImages());
            System.out.println("✅ Letter: " + dto.getLetter());
            System.out.println("✅ Title: " + dto.getTitle());
            return dto;
        } else {
            throw new RuntimeException("Failed to generate letter: " + response.getStatusCode());
        }
    }

    // ✅ 상태 완료 시 스케줄러 종료
    private void stopTraining() {
        trainingInProgress = false;
        if (!executor.isShutdown()) {
            System.out.println("Stopping training status check...");
            executor.shutdown(); // 스케줄 종료
        }
    }
}
