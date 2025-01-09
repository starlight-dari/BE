package com.example.startlight.pet.controller;

import com.example.startlight.pet.dto.PetDto;
import com.example.startlight.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    @PostMapping("/create")
    public ResponseEntity<PetDto> createPet(
            @RequestBody PetDto petDto
    ) {
        PetDto responsePetDto = petService.createPet(petDto);
        return ResponseEntity.status(HttpStatus.OK).body(responsePetDto);
    }
}
