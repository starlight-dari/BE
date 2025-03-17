package com.example.startlight.memoryAlbum.controller;

import com.example.startlight.memoryAlbum.service.MemoryAlbumFlaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("memory-album")
public class MemoryAlbumController {
    private final MemoryAlbumFlaskService memoryAlbumFlaskService;

    @PostMapping()
    public void createMemoryAlbum() {
        memoryAlbumFlaskService.generateMemoryAlbum();
    }
}
