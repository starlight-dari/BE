package com.example.startlight.pet.controller;

import com.example.startlight.pet.dto.*;
import com.example.startlight.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pets")
@Slf4j
public class PetController {
    private final PetService petService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PetIdRepDto> createPet(
            @ModelAttribute PetReqDto petReqDto
    ) throws IOException {
        PetIdRepDto responsePetRepDto = petService.createPet(petReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(responsePetRepDto);
    }

    @PatchMapping("/{petId}")
    public ResponseEntity<PetRepDto> updatePet(@PathVariable Long petId, @RequestBody PetUpdateReqDto petUpdateReqDto) {
        PetRepDto responsePetRepDto = petService.updatePet(petId, petUpdateReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(responsePetRepDto);
    }

    @GetMapping()
    public ResponseEntity<List<PetRepDto>> getAllPets() {
        List<PetRepDto> petRepDtoList = petService.getPets();
        return ResponseEntity.status(HttpStatus.OK).body(petRepDtoList);
    }

    @GetMapping("/{petId}/stars")
    public ResponseEntity<PetStarListRepDto> getList(@PathVariable Long petId) {
        PetStarListRepDto petStarList = petService.getPetStarList(petId);
        return ResponseEntity.status(HttpStatus.OK).body(petStarList);
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<String> deletePet(@PathVariable Long petId) {
        petService.deletePet(petId);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted pet with id " + petId);
    }
}
