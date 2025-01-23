package com.example.startlight.pet.service;

import com.example.startlight.member.dao.MemberDao;
import com.example.startlight.member.repository.MemberRepository;
import com.example.startlight.pet.dao.PetDao;
import com.example.startlight.pet.dto.PetDto;
import com.example.startlight.pet.entity.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService{
    private final PetDao petDao;
    private final MemberRepository memberRepository;
    @Override
    public PetDto createPet(PetDto petDto) {
        Pet pet = petDao.createPet(Pet.toEntity(petDto,memberRepository));
        return PetDto.toDto(pet);
    }
}
