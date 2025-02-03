package com.example.startlight.memoryStar.contoller;

import com.example.startlight.memoryStar.dto.MemoryStarRepDto;
import com.example.startlight.memoryStar.dto.MemoryStarReqDto;
import com.example.startlight.memoryStar.dto.MemoryStarUpdateDto;
import com.example.startlight.memoryStar.service.MemoryStarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memory")
public class MemoryStarContoller {
    private final MemoryStarService memoryStarService;

    @PostMapping("/create")
    public ResponseEntity<MemoryStarRepDto> createMemoryStar(
            @RequestBody MemoryStarReqDto memoryStarReqDto
    ) {
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
            @RequestBody MemoryStarUpdateDto memoryStarReqDto
    ) {
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
