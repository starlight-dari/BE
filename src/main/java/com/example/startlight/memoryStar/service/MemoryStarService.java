package com.example.startlight.memoryStar.service;

import com.example.startlight.memoryStar.dao.MemoryStarDao;
import com.example.startlight.memoryStar.dto.MemoryStarRepDto;
import com.example.startlight.memoryStar.dto.MemoryStarReqDto;
import com.example.startlight.memoryStar.entity.MemoryStar;
import com.example.startlight.pet.dao.PetDao;
import com.example.startlight.pet.entity.Pet;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemoryStarService {
    private final MemoryStarDao memoryStarDao;
    private final PetDao petDao;

    public MemoryStarRepDto selectStarById(Long id) {
        MemoryStar memoryStar = memoryStarDao.selectMemoryStarById(id);
        return MemoryStarRepDto.toDto(memoryStar);
    }

    public MemoryStarRepDto createMemoryStar(MemoryStarReqDto memoryStarReqDto) {
        Pet selectedPet = petDao.selectPet(memoryStarReqDto.getPet_id());
        MemoryStar memoryStar = MemoryStar.toEntity(memoryStarReqDto, selectedPet);
        MemoryStar createdStar = memoryStarDao.createMemoryStar(memoryStar);
        return MemoryStarRepDto.toDto(createdStar);
    }
}
