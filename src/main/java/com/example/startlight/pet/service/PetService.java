package com.example.startlight.pet.service;

import com.example.startlight.pet.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PetService {
    PetIdRepDto createPet(PetReqDto petReqDto) throws IOException;
    PetRepDto updatePet(Long petId, PetUpdateReqDto petUpdateReqDto);
    List<PetRepDto> getPets();
    List<PetSimpleRepDto> getPetSimple(Long userId);
    PetStarListRepDto getPetStarList(Long petId);
    void deletePet(Long petId);
}
