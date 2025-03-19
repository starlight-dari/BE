package com.example.startlight.memoryAlbum.controller;

import com.example.startlight.memoryAlbum.dto.AlbumByPetRepDto;
import com.example.startlight.memoryAlbum.dto.MemoryAlbumRepDto;
import com.example.startlight.memoryAlbum.dto.MemoryAlbumReqDto;
import com.example.startlight.memoryAlbum.dto.MemoryAlbumSimpleDto;
import com.example.startlight.memoryAlbum.service.MemoryAlbumFlaskService;
import com.example.startlight.memoryAlbum.service.MemoryAlbumScheduleService;
import com.example.startlight.memoryAlbum.service.MemoryAlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("memory-album")
public class MemoryAlbumController {
    private final MemoryAlbumFlaskService memoryAlbumFlaskService;
    private final MemoryAlbumScheduleService memoryAlbumScheduleService;
    private final MemoryAlbumService memoryAlbumService;

//    @GetMapping()
//    public ResponseEntity<?> createMemoryAlbum() {
//        memoryAlbumFlaskService.generateMemoryAlbum();
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/status")
    public ResponseEntity<List<AlbumByPetRepDto>> getAllMemoryAlbumStatus() {
        List<AlbumByPetRepDto> memoryAlbumStatusByPet = memoryAlbumService.getMemoryAlbumStatusByPet();
        return ResponseEntity.ok(memoryAlbumStatusByPet);
    }

//    @PostMapping("/test")
//    public ResponseEntity<?> testMemoryAlbum() {
//        memoryAlbumScheduleService.createAlbum(LocalDateTime.now());
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<MemoryAlbumSimpleDto>> getMemoryAlbumByPetId(@PathVariable("petId") Long petId) {
        List<MemoryAlbumSimpleDto> memoryAlbumByPet = memoryAlbumService.getMemoryAlbumByPet(petId);
        return ResponseEntity.status(HttpStatus.OK).body(memoryAlbumByPet);
    }

    @GetMapping("/letter/{letterId}")
    public ResponseEntity<MemoryAlbumRepDto> getMemoryAlbumByLetterId(@PathVariable("letterId") Long letterId) {
        MemoryAlbumRepDto memoryAlbum = memoryAlbumService.getMemoryAlbumAndUpdateRead(letterId);
        return ResponseEntity.status(HttpStatus.OK).body(memoryAlbum);
    }

    @PostMapping()
    public ResponseEntity<MemoryAlbumRepDto> addMemoryAlbum(@RequestBody MemoryAlbumReqDto memoryAlbumReqDto) {
        MemoryAlbumRepDto memoryAlbum = memoryAlbumService.createMemoryAlbum(memoryAlbumReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(memoryAlbum);
    }
}
