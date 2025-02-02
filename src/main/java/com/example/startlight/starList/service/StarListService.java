package com.example.startlight.starList.service;

import com.example.startlight.pet.dao.PetDao;
import com.example.startlight.pet.entity.Pet;
import com.example.startlight.pet.repository.PetRepository;
import com.example.startlight.starList.dao.StarListDao;
import com.example.startlight.starList.dto.StarListRepDto;
import com.example.startlight.starList.dto.StarListReqDto;
import com.example.startlight.starList.entity.StarList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StarListService {
    private final StarListDao starListDao;
    private final PetDao petDao;

    public List<StarListRepDto> createList(Long petId, List<StarListReqDto> starListReqDtos) {
        Pet selectedPet = petDao.selectPet(petId);
        List<StarList> starLists = starListReqDtos.stream()
                .map(starListReqDto -> StarList.builder()
                        .pet(selectedPet)  // Pet 정보 가져오기
                        .x_star(starListReqDto.getX_star())
                        .y_star(starListReqDto.getY_star())
                        .build()
                ).collect(Collectors.toList()); // 리스트로 변환
        List<StarList> createdStarList = starListDao.createStarList(starLists);
        List<StarListRepDto> starListReqDtoList = createdStarList.stream()
                .map(starList -> StarListRepDto.builder()
                        .starList_id(starList.getStarList_id())
                        .x_star(starList.getX_star())
                        .y_star(starList.getY_star()).build()).collect(Collectors.toList());
        return starListReqDtoList;
    }
}
