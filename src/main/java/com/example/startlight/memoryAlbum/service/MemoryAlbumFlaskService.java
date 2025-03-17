package com.example.startlight.memoryAlbum.service;

import com.example.startlight.memoryAlbum.dto.ImgRequestDto;
import com.example.startlight.memoryAlbum.dto.LetterGenerateDto;
import com.example.startlight.pet.entity.Personality;
import com.example.startlight.pet.service.FlaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemoryAlbumFlaskService {
    @Value("${ml.api}")
    public String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final FlaskService flaskService;
    private boolean trainingInProgress = true;

    public void generateMemoryAlbum() {
        List<String> images = new ArrayList<>();
        images.add("https://starlightbucket.s3.amazonaws.com/ml_test/kkong1.jpg");
        images.add("https://starlightbucket.s3.amazonaws.com/ml_test/kkong2.jpg");
        images.add("https://starlightbucket.s3.amazonaws.com/ml_test/kkong3.jpg");
        images.add("https://starlightbucket.s3.amazonaws.com/ml_test/kkong4.jpg");
        images.add("https://starlightbucket.s3.amazonaws.com/ml_test/kkong5.jpg");
        ImgRequestDto imgRequestDto = ImgRequestDto.builder().images(images).build();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("images", imgRequestDto);

        ResponseEntity<String> tResponseEntity = flaskService.sendPostRequest(apiUrl + "/letter_train", requestBody, String.class);
        if(tResponseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Training started...");
            trainingInProgress = true;
            checkTrainingStatus();
        }
        else {
            throw new RuntimeException("추가 Flask 서버 응답 실패: " + tResponseEntity.getStatusCode());
        }
    }

    @Scheduled(fixedDelay = 10000) // 10초마다 실행
    public void checkTrainingStatus() {
        if (!trainingInProgress) {
            return;
        }

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl + "/training_status", String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String status = response.getBody();

                System.out.println("Training status: " + status);

                if ("completed".equalsIgnoreCase(status)) {
                    System.out.println("Training finished with status: " + status);

                    // training 완료되면 letterGenerate 실행
                    String result = letterGenerate();
                    System.out.println("Generated Letter: " + result);

                    // 상태 완료 후 반복 중지
                    trainingInProgress = false;
                } else if ("failed".equalsIgnoreCase(status)) {
                    System.out.println("Training failed with status: " + status);

                    // 실패 시 반복 중지
                    trainingInProgress = false;
                }
            }
        } catch (Exception e) {
            System.out.println("Error while checking training status: " + e.getMessage());
        }
    }

    public String letterGenerate() {
        Personality personality = Personality.CALM;
        String breed = "고양이";
        String text = "택배라도 도착하는 날이면 넌 언제나 가장 먼저 달려와 상자를 차지했지. 아직 내용물을 다 꺼내지도 않았는데 벌써 네 몸을 쏙 집어넣고는 “이제 내 거야!”라고 말하는 듯한 표정을 지었어. 네 몸보다 작은 상자에도 어떻게든 들어가려고 애쓰는 모습이 너무 귀여워서, 나는 늘 카메라를 꺼내 들 수밖에 없었어.\n" +
                "    이 날도 그랬지. 작은 상자를 바닥에 두자마자 네가 후다닥 달려와 쏙 들어가 앉았어. 그러고는 저렇게 나를 빤히 쳐다봤지. ‘이거 봐, 나 딱 맞지?’라는 듯한 자부심 가득한 표정으로. 사실 네 몸이 상자보다 살짝 커서 털이 삐죽삐죽 튀어나왔지만, 그건 중요하지 않았겠지. 네가 원한다면 그곳이 최고의 자리였을 테니까.\n" +
                "    작은 공간을 찾아 꼭꼭 들어가 있던 너, 그 모습이 너무 익숙하고 사랑스러웠어. 지금도 어디선가 네가 꼭 맞는 작은 공간을 찾아 쏙 들어가, 나를 기다리고 있을 것만 같아.\n" +
                "    어디에 있든, 네가 좋아하는 곳에서 편히 쉬고 있길 바라. \uD83D\uDC9B\uD83D\uDCE6\uD83D\uDC3E";
        List<String> texts = new ArrayList<>();
        texts.add(text);
        LetterGenerateDto generateDto = LetterGenerateDto.builder()
                .character(personality.getDescription())
                .breed(breed)
                .texts(texts)
                .pet_id(1L)
                .letter_id(1L).build();

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // 요청 엔터티 생성
        HttpEntity<LetterGenerateDto> requestEntity = new HttpEntity<>(generateDto, headers);

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl + "/letter_generate",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Response: " + response.getBody());
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to generate letter: " + response.getStatusCode());
        }
    }
}
