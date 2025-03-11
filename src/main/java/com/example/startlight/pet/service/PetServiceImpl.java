package com.example.startlight.pet.service;

import com.example.startlight.kakao.util.UserUtil;
import com.example.startlight.member.repository.MemberRepository;
import com.example.startlight.pet.dao.PetDao;
import com.example.startlight.pet.dto.*;
import com.example.startlight.pet.entity.Edge;
import com.example.startlight.pet.entity.Pet;
import com.example.startlight.s3.service.S3Service;
import com.example.startlight.starList.dto.StarListRepDto;
import com.example.startlight.starList.service.StarListService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService{
    private final PetDao petDao;
    private final StarListService starListService;
    private final S3Service s3Service;
    private final FlaskService flaskService;
    private final MemberRepository memberRepository;

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

        ResponseEntity<String> response = flaskService.sendPostRequest(flaskApiUrl, requestBody, String.class);

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
        FlaskResponseDto responseDto = flaskService.testMLApi();

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

    @Override
    public PetRepDto updatePet(Long petId, PetUpdateReqDto petUpdateReqDto) {
        Pet pet = petDao.updatePet(petId, petUpdateReqDto);
        return PetRepDto.toDto(pet);
    }

    @Override
    public List<PetRepDto> getPets() {
        Long userId = UserUtil.getCurrentUserId();
        List<Pet> pets = petDao.selectAllPet(userId);
        return pets.stream()
                .map(PetRepDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetSimpleRepDto> getPetSimple(Long userId) {
        List<Pet> pets = petDao.selectAllPet(userId);
        return pets.stream()
                .map(PetSimpleRepDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PetStarListRepDto getPetStarList(Long petId) {
        List<Edge> edgesByPetId = petDao.getEdgesByPetId(petId);
        List<StarListRepDto> list = starListService.getList(petId);
        return PetStarListRepDto.builder()
                .petId(petId)
                .starList(list)
                .edges(edgesByPetId)
                .build();
    }

    @Override
    public void deletePet(Long petId) {
        petDao.deletePet(petId);
    }


}
