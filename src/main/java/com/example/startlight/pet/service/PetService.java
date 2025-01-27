package com.example.startlight.pet.service;

import com.example.startlight.pet.dto.PetRepDto;
import com.example.startlight.pet.dto.PetReqDto;
import com.example.startlight.pet.dto.PetUpdateReqDto;

import java.util.List;

public interface PetService {
    PetRepDto createPet(PetReqDto petReqDto);
    PetRepDto updatePet(PetUpdateReqDto petUpdateReqDto);
    List<PetRepDto> getPets();
}
