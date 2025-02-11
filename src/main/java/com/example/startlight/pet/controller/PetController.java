package com.example.startlight.pet.controller;

import com.example.startlight.pet.dto.PetRepDto;
import com.example.startlight.pet.dto.PetReqDto;
import com.example.startlight.pet.dto.PetUpdateReqDto;
import com.example.startlight.pet.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
@Slf4j
public class PetController {
    private final PetService petService;

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PetRepDto> createPet(
            @ModelAttribute PetReqDto petReqDto
    ) throws IOException {
        PetRepDto responsePetRepDto = petService.createPet(petReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(responsePetRepDto);
    }

    @PatchMapping("/update")
    public ResponseEntity<PetRepDto> updatePet(@RequestBody PetUpdateReqDto petUpdateReqDto) {
        PetRepDto responsePetRepDto = petService.updatePet(petUpdateReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(responsePetRepDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PetRepDto>> getAllPets() {
        List<PetRepDto> petRepDtoList = petService.getPets();
        return ResponseEntity.status(HttpStatus.OK).body(petRepDtoList);
    }
}
