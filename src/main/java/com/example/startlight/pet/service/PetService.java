package com.example.startlight.pet.service;

import com.example.startlight.pet.dto.PetRepDto;
import com.example.startlight.pet.dto.PetReqDto;
import com.example.startlight.pet.dto.PetUpdateReqDto;

public interface PetService {
    PetRepDto createPet(PetReqDto petReqDto);
    PetRepDto updatePet(PetUpdateReqDto petUpdateReqDto);
}
