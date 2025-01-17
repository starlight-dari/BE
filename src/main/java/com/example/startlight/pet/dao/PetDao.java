package com.example.startlight.pet.dao;

import com.example.startlight.pet.entity.Pet;

public interface PetDao {
    Pet createPet(Pet pet);

    Pet selectPet(Long pet_id);

    Pet updatePet(Pet pet);
}
