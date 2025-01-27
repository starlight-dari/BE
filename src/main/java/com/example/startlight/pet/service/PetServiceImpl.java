package com.example.startlight.pet.service;

import com.example.startlight.member.repository.MemberRepository;
import com.example.startlight.pet.dao.PetDao;
import com.example.startlight.pet.dto.PetRepDto;
import com.example.startlight.pet.dto.PetReqDto;
import com.example.startlight.pet.dto.PetUpdateReqDto;
import com.example.startlight.pet.entity.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService{
    private final PetDao petDao;
    private final MemberRepository memberRepository;
    @Override
    public PetRepDto createPet(PetReqDto petReqDto) {
        //Long userId = UserUtil.getCurrentUserId();
        Long userId = 3879188713L;
        Pet pet = petDao.createPet(Pet.toEntity(petReqDto,userId,memberRepository));
        return PetRepDto.toDto(pet);
    }

    @Override
    public PetRepDto updatePet(PetUpdateReqDto petUpdateReqDto) {
        Pet pet = petDao.updatePet(petUpdateReqDto);
        return PetRepDto.toDto(pet);
    }

    @Override
    public List<PetRepDto> getPets() {
        //Long userId = UserUtil.getCurrentUserId();
        Long userId = 3879188713L;
        List<Pet> pets = petDao.selectAllPet(userId);
        return pets.stream()
                .map(PetRepDto::toDto)
                .collect(Collectors.toList());
    }
}
