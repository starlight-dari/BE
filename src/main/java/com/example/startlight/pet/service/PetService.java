package com.example.startlight.pet.service;

import com.example.startlight.pet.dto.PetRepDto;
import com.example.startlight.pet.dto.PetReqDto;
import com.example.startlight.pet.dto.PetUpdateReqDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PetService {
    PetRepDto createPet(PetReqDto petReqDto) throws IOException;
    PetRepDto updatePet(PetUpdateReqDto petUpdateReqDto);
    List<PetRepDto> getPets();
}
