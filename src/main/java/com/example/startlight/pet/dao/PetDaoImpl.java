package com.example.startlight.pet.dao;

import com.example.startlight.pet.entity.Pet;
import com.example.startlight.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PetDaoImpl implements PetDao{

    private final PetRepository petRepository;
    @Override
    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }
}
