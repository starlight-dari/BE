package com.example.startlight.pet.service;

import com.example.startlight.member.repository.MemberRepository;
import com.example.startlight.pet.dao.PetDao;
import com.example.startlight.pet.dto.PetRepDto;
import com.example.startlight.pet.dto.PetReqDto;
import com.example.startlight.pet.dto.PetUpdateReqDto;
import com.example.startlight.pet.entity.Pet;
import com.example.startlight.s3.service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService{
    private final PetDao petDao;
    private final S3Service s3Service;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public PetRepDto createPet(PetReqDto petReqDto) throws IOException {
        //TODO
        // Long userId = UserUtil.getCurrentUserId();
        Long userId = 3879188713L;
        Pet pet = petDao.createPet(Pet.toEntity(petReqDto,userId,memberRepository));
        String uploadFile = s3Service.uploadPetImg(petReqDto.getPet_img(), String.valueOf(pet.getPet_id()));
        pet.setPet_img(uploadFile);

        //Flask 서버 연결
        String flaskApiUrl = "http://localhost:5000/process";
        
        return PetRepDto.toDto(pet);
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
