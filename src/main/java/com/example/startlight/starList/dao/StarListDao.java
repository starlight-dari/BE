package com.example.startlight.starList.dao;

import com.example.startlight.pet.entity.Pet;
import com.example.startlight.starList.entity.StarList;
import com.example.startlight.starList.repository.StarListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StarListDao {

    private final StarListRepository starListRepository;

    public List<StarList> createStarList(List<List<Integer>> majorPoints, Pet selectedPet) {
        List<StarList> starLists = majorPoints.stream()
                .map(point -> StarList.builder()
                        .pet(selectedPet)
                        .x_star(point.get(0))
                        .y_star(point.get(1))
                        .index_id((Integer) majorPoints.indexOf(point))
                        .written(false)
                        .build())
                .collect(Collectors.toList());
        return starListRepository.saveAll(starLists);
    }

    public List<StarList> findAllStarList(Long petId) {
        return starListRepository.findByPetId(petId);
    }

    public StarList findStarListById(Long id) {
        return starListRepository.findById(id).orElse(null);
    }

    @Transactional
    public StarList updateStarWritten(Long id) {
        StarList starList = findStarListById(id);
        starList.updateStarList();
        return starList;
    }
}
