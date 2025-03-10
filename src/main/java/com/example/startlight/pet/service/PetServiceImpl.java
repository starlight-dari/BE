package com.example.startlight.pet.service;

import com.example.startlight.kakao.util.UserUtil;
import com.example.startlight.member.repository.MemberRepository;
import com.example.startlight.pet.dao.PetDao;
import com.example.startlight.pet.dto.*;
import com.example.startlight.pet.entity.Edge;
import com.example.startlight.pet.entity.Pet;
import com.example.startlight.s3.service.S3Service;
import com.example.startlight.starList.dao.StarListDao;
import com.example.startlight.starList.dto.StarListRepDto;
import com.example.startlight.starList.service.StarListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService{
    private final PetDao petDao;
    private final StarListService starListService;
    private final S3Service s3Service;
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;

    @Override
    @Transactional
    public PetIdRepDto createPet(PetReqDto petReqDto) throws IOException {
        Long userId = UserUtil.getCurrentUserId();
        Pet pet = petDao.createPet(Pet.toEntity(petReqDto,userId,memberRepository));
        String uploadFile = s3Service.uploadPetImg(petReqDto.getPet_img(), String.valueOf(pet.getPet_id()));
        pet.setPet_img(uploadFile);

        /*
        // Step 1: 첫 번째 Flask API 호출
        String flaskApiUrl = "http://localhost:4000/stars_run_pidinet";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("image_url", uploadFile);

        ResponseEntity<String> response = sendPostRequest(flaskApiUrl, requestBody);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("✅ Flask 서버에서 응답 성공: " + response.getBody());
            // Step 2: 응답이 200일 경우 추가 Flask API 호출
            FlaskResponseDto flaskResponseDto = processImgFlaskApi(uploadFile, petReqDto.getSelected_x(), petReqDto.getSelected_y());
            System.out.println("✅ 추가 Flask 응답 성공: " + flaskResponseDto.toString());
        } else {
            System.out.println("❌ Flask 서버에서 응답 실패: " + response.getStatusCode());
            throw new RuntimeException("Flask 서버 응답 실패");
        }
        */
        FlaskResponseDto responseDto = testMLApi();

        //별자리 정보 저장
        pet.setSvg_path(responseDto.getSvgPath());
        List<Edge> edges = responseDto.getEdges().stream()
                .map(e -> new Edge(e.get(0), e.get(1)))
                .collect(Collectors.toList());
        pet.setEdges(edges);
        List<StarListRepDto> list = starListService.createList(pet.getPet_id(), responseDto.getMajorPoints());
        return PetIdRepDto.builder()
                .petId(pet.getPet_id()).build();
    }

    /**
     * 공통 POST 요청 로직
     */
    private ResponseEntity<String> sendPostRequest(String url, Map<String, String> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Flask 서버 요청 실패: " + e.getMessage());
        }
    }

    private ResponseEntity<String> sendPostRequestObject(String url, Map<String, Object> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Flask 서버 요청 실패: " + e.getMessage());
        }
    }

    /**
     * 추가 Flask API 호출
     */
    private FlaskResponseDto processImgFlaskApi(String imageUrl, Long selectedX,Long selectedY) {
        String additionalApiUrl = "http://localhost:4000/stars_process_image";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("image_url", imageUrl);
        requestBody.put("point", new Long[]{selectedX, selectedY});

        ResponseEntity<String> response = sendPostRequestObject(additionalApiUrl, requestBody);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                // ✅ JSON 응답을 DTO로 매핑
                ObjectMapper objectMapper = new ObjectMapper();
                FlaskResponseDto flaskResponse = objectMapper.readValue(response.getBody(), FlaskResponseDto.class);
                return flaskResponse;
            } catch (Exception e) {
                throw new RuntimeException("Flask 응답 매핑 실패: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("추가 Flask 서버 응답 실패: " + response.getStatusCode());
        }
    }

    private FlaskResponseDto testMLApi() {
        FlaskResponseDto flaskResponseDto = new FlaskResponseDto();
        flaskResponseDto.setSvgPath("https://starlightbucket.s3.amazonaws.com/test_user/kkong9.svg");
        // ✅ edges 설정
        List<List<Integer>> edges = Arrays.asList(
                Arrays.asList(0, 1), Arrays.asList(1, 14), Arrays.asList(3, 10), Arrays.asList(4, 12),
                Arrays.asList(6, 5), Arrays.asList(6, 7), Arrays.asList(6, 14), Arrays.asList(7, 2),
                Arrays.asList(8, 2), Arrays.asList(9, 8), Arrays.asList(9, 10), Arrays.asList(11, 12),
                Arrays.asList(13, 3), Arrays.asList(13, 12)
        );
        flaskResponseDto.setEdges(edges);

        // ✅ majorPoints 설정
        List<List<Integer>> majorPoints = Arrays.asList(
                Arrays.asList(459, 200), Arrays.asList(470, 255), Arrays.asList(256, 346),
                Arrays.asList(134, 273), Arrays.asList(129, 121), Arrays.asList(310, 296),
                Arrays.asList(314, 335), Arrays.asList(287, 374), Arrays.asList(253, 298),
                Arrays.asList(258, 253), Arrays.asList(175, 271), Arrays.asList(157, 217),
                Arrays.asList(110, 213), Arrays.asList(116, 252), Arrays.asList(367, 339)
        );
        flaskResponseDto.setMajorPoints(majorPoints);
        return flaskResponseDto;
    }

    @Override
    public PetRepDto updatePet(PetUpdateReqDto petUpdateReqDto) {
        Pet pet = petDao.updatePet(petUpdateReqDto);
        return PetRepDto.toDto(pet);
    }

    @Override
    public List<PetRepDto> getPets() {
        //TODO
        // Long userId = UserUtil.getCurrentUserId();
        Long userId = 3879188713L;
        List<Pet> pets = petDao.selectAllPet(userId);
        return pets.stream()
                .map(PetRepDto::toDto)
                .collect(Collectors.toList());
    }
}
