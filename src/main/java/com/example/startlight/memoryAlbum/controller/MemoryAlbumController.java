package com.example.startlight.memoryAlbum.controller;

import com.example.startlight.memoryAlbum.dto.AlbumByPetRepDto;
import com.example.startlight.memoryAlbum.service.MemoryAlbumFlaskService;
import com.example.startlight.memoryAlbum.service.MemoryAlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("memory-album")
public class MemoryAlbumController {
    private final MemoryAlbumFlaskService memoryAlbumFlaskService;
    private final MemoryAlbumService memoryAlbumService;

    @GetMapping()
    public ResponseEntity<?> createMemoryAlbum() {
        memoryAlbumFlaskService.generateMemoryAlbum();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    public ResponseEntity<List<AlbumByPetRepDto>> getAllMemoryAlbumStatus() {
        List<AlbumByPetRepDto> memoryAlbumStatusByPet = memoryAlbumService.getMemoryAlbumStatusByPet();
        return ResponseEntity.ok(memoryAlbumStatusByPet);
    }
}
