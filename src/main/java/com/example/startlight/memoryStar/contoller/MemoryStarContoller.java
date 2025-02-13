package com.example.startlight.memoryStar.contoller;

import com.example.startlight.memoryStar.dto.MemoryStarRepDto;
import com.example.startlight.memoryStar.dto.MemoryStarReqDto;
import com.example.startlight.memoryStar.dto.MemoryStarUpdateDto;
import com.example.startlight.memoryStar.service.MemoryStarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memory")
public class MemoryStarContoller {
    private final MemoryStarService memoryStarService;

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MemoryStarRepDto> createMemoryStar(
            @ModelAttribute MemoryStarReqDto memoryStarReqDto
    ) throws IOException {
        MemoryStarRepDto memoryStar = memoryStarService.createMemoryStar(memoryStarReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(memoryStar);
    }

    @GetMapping("/selectEach")
    public ResponseEntity<MemoryStarRepDto> selectMemoryStar(@RequestParam Long memoryId) {
        MemoryStarRepDto memoryStarRepDto = memoryStarService.selectStarById(memoryId);
        return ResponseEntity.status(HttpStatus.OK).body(memoryStarRepDto);
    }

    @PatchMapping("/update")
    public ResponseEntity<MemoryStarRepDto> updateMemoryStar(
            @ModelAttribute MemoryStarUpdateDto memoryStarReqDto
    ) throws IOException {
        MemoryStarRepDto memoryStar = memoryStarService.updateMemoryStar(memoryStarReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(memoryStar);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMemoryStar(
            @RequestParam Long memoryId
    ) {
        memoryStarService.deleteMemoryStar(memoryId);
        return ResponseEntity.status(HttpStatus.OK).body("Success delete memory star id : " + memoryId);
    }
}
