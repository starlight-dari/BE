package com.example.startlight.pet.controller;

import com.example.startlight.pet.dto.PetRepDto;
import com.example.startlight.pet.dto.PetReqDto;
import com.example.startlight.pet.dto.PetUpdateReqDto;
import com.example.startlight.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    @PostMapping("/create")
    public ResponseEntity<PetRepDto> createPet(
            @RequestBody PetReqDto petReqDto
    ) {
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
