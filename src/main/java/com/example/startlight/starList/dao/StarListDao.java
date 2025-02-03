package com.example.startlight.starList.dao;

import com.example.startlight.pet.dao.PetDao;
import com.example.startlight.pet.entity.Pet;
import com.example.startlight.pet.repository.PetRepository;
import com.example.startlight.starList.entity.StarList;
import com.example.startlight.starList.repository.StarListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StarListDao {

    private final StarListRepository starListRepository;

    public List<StarList> createStarList(List<StarList> starList) {
        return starListRepository.saveAll(starList);
    }

    public List<StarList> findAllStarList(Long petId) {
        return starListRepository.findByPetId(petId);
    }

    public StarList findStarListById(Long id) {
        return starListRepository.findById(id).orElse(null);
    }

}
