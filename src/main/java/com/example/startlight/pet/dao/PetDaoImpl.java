package com.example.startlight.pet.dao;

import com.example.startlight.pet.entity.Pet;
import com.example.startlight.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PetDaoImpl implements PetDao{

    private final PetRepository petRepository;
    @Override
    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Pet selectPet(Long pet_id) {
        Optional<Pet> selectedPet = petRepository.findById(pet_id);
        if(selectedPet.isPresent()) {
            return selectedPet.get();
        }
        throw new NoSuchElementException("Member not found with id: " + pet_id);
    }

    @Override
    public Pet updatePet(Pet pet) {
        return null;
    }
}
