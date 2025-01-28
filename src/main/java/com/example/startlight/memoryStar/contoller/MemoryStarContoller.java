package com.example.startlight.memoryStar.contoller;

import com.example.startlight.memoryStar.dto.MemoryStarRepDto;
import com.example.startlight.memoryStar.dto.MemoryStarReqDto;
import com.example.startlight.memoryStar.service.MemoryStarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/star")
public class MemoryStarContoller {
    private final MemoryStarService memoryStarService;

    @PostMapping("/create")
    public ResponseEntity<MemoryStarRepDto> createUser(
            @RequestBody MemoryStarReqDto memoryStarReqDto
    ) {
        MemoryStarRepDto memoryStar = memoryStarService.createMemoryStar(memoryStarReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(memoryStar);
    }

    @GetMapping("/selectEach")
    public ResponseEntity<MemoryStarRepDto> selectUser(@RequestParam Long starId) {
        MemoryStarRepDto memoryStarRepDto = memoryStarService.selectStarById(starId);
        return ResponseEntity.status(HttpStatus.OK).body(memoryStarRepDto);
    }
}
