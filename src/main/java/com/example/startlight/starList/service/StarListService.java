package com.example.startlight.starList.service;

import com.example.startlight.pet.dao.PetDao;
import com.example.startlight.pet.entity.Pet;
import com.example.startlight.pet.repository.PetRepository;
import com.example.startlight.starList.dao.StarListDao;
import com.example.startlight.starList.dto.StarListRepDto;
import com.example.startlight.starList.dto.StarListReqDto;
import com.example.startlight.starList.entity.StarList;
import com.example.startlight.starList.mapper.StarListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StarListService {
    private final StarListDao starListDao;
    private final PetDao petDao;
    private final StarListMapper mapper = StarListMapper.INSTANCE;

    public List<StarListRepDto> createList(Long petId, List<StarListReqDto> starListReqDtos) {
        Pet selectedPet = petDao.selectPet(petId);

        // DTO → Entity 변환
        List<StarList> starLists = mapper.toEntityList(starListReqDtos, selectedPet);

        // DB 저장
        List<StarList> createdStarList = starListDao.createStarList(starLists);

        // Entity → DTO 변환
        return mapper.toDtoList(createdStarList);
    }

    public List<StarListRepDto> getList(Long petId) {
        List<StarList> starList = starListDao.findAllStarList(petId);
        return mapper.toDtoList(starList);
    }
}
