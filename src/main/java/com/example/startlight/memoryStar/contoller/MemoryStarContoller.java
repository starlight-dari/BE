package com.example.startlight.memoryStar.contoller;

import com.example.startlight.memComment.dto.MemCommentRepDto;
import com.example.startlight.memoryStar.dto.*;
import com.example.startlight.memoryStar.service.MemoryStarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<MemoryStarRepWithComDto> selectMemoryStar(@RequestParam Long memoryId) {
        MemoryStarRepWithComDto memoryStarRepDto = memoryStarService.selectStarById(memoryId);
        return ResponseEntity.status(HttpStatus.OK).body(memoryStarRepDto);
    }

    @PatchMapping("/update")
    public ResponseEntity<MemoryStarRepWithComDto> updateMemoryStar(
            @ModelAttribute MemoryStarUpdateDto memoryStarReqDto
    ) throws IOException {
        MemoryStarRepWithComDto memoryStar = memoryStarService.updateMemoryStar(memoryStarReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(memoryStar);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMemoryStar(
            @RequestParam Long memoryId
    ) {
        memoryStarService.deleteMemoryStar(memoryId);
        return ResponseEntity.status(HttpStatus.OK).body("Success delete memory star id : " + memoryId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MemoryStarSimpleRepDto>> getAllMemoryStar() {
        List<MemoryStarSimpleRepDto> allPublicMemoryStar = memoryStarService.findAllPublicMemoryStar();
        return ResponseEntity.status(HttpStatus.OK).body(allPublicMemoryStar);
    }

    //like

    @PostMapping("/createLike")
    public ResponseEntity<MemoryStarRepDto> createLikeMemoryStar(@RequestParam Long memoryId) {
        MemoryStarRepDto memoryStarRepDto = memoryStarService.createLike(memoryId);
        return ResponseEntity.status(HttpStatus.OK).body(memoryStarRepDto);
    }

    @PostMapping("/deleteLike")
    public ResponseEntity<MemoryStarRepDto> deleteLikeMemoryStar(@RequestParam Long memoryId) {
        MemoryStarRepDto memoryStarRepDto = memoryStarService.deleteLike(memoryId);
        return ResponseEntity.status(HttpStatus.OK).body(memoryStarRepDto);
    }
}
