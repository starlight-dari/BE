package com.example.startlight.memoryStar.contoller;

import com.example.startlight.member.service.MemberService;
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
@RequestMapping("/memory-stars")
public class MemoryStarContoller {
    private final MemoryStarService memoryStarService;
    private final MemberService memberService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MemoryStarRepDto> createMemoryStar(
            @ModelAttribute MemoryStarReqDto memoryStarReqDto
    ) throws IOException {
        MemoryStarRepDto memoryStar = memoryStarService.createMemoryStar(memoryStarReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(memoryStar);
    }

    @GetMapping("/{memoryId}")
    public ResponseEntity<MemoryStarRepWithComDto> selectMemoryStar(@PathVariable Long memoryId) {
        MemoryStarRepWithComDto memoryStarRepDto = memoryStarService.selectStarById(memoryId);
        return ResponseEntity.status(HttpStatus.OK).body(memoryStarRepDto);
    }

    @PatchMapping("/{memoryId}")
    public ResponseEntity<MemoryStarRepWithComDto> updateMemoryStar(
            @PathVariable Long memoryId,
            @ModelAttribute MemoryStarUpdateDto memoryStarReqDto
    ) throws IOException {
        MemoryStarRepWithComDto memoryStar = memoryStarService.updateMemoryStar(memoryId, memoryStarReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(memoryStar);
    }

    @DeleteMapping("/{memoryId}")
    public ResponseEntity<String> deleteMemoryStar(
            @RequestParam Long memoryId
    ) {
        memoryStarService.deleteMemoryStar(memoryId);
        return ResponseEntity.status(HttpStatus.OK).body("Success delete memory star id : " + memoryId);
    }

    @GetMapping("/public")
    public ResponseEntity<List<MemoryStarSimpleRepDto>> getAllMemoryStar() {
        List<MemoryStarSimpleRepDto> allPublicMemoryStar = memoryStarService.findAllPublicMemoryStar();
        return ResponseEntity.status(HttpStatus.OK).body(allPublicMemoryStar);
    }

    @GetMapping()
    public ResponseEntity<MemoryStarListWithNumDto> getMyMemoryStar() {
        List<MemoryStarSimpleRepDto> allPublicMemoryStar = memoryStarService.findAllMyMemoryStar();
        Integer memoryNumber = memberService.getMemoryNumber();
        MemoryStarListWithNumDto buildDto = MemoryStarListWithNumDto.builder()
                .memoryNumber(memoryNumber)
                .memoryStarSimpleRepDtoList(allPublicMemoryStar)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(buildDto);
    }

    //like

    @PostMapping("/{memoryId}/likes")
    public ResponseEntity<MemoryStarRepDto> createLikeMemoryStar(@PathVariable Long memoryId) {
        MemoryStarRepDto memoryStarRepDto = memoryStarService.createLike(memoryId);
        return ResponseEntity.status(HttpStatus.OK).body(memoryStarRepDto);
    }

    @DeleteMapping("/{memoryId}/likes")
    public ResponseEntity<MemoryStarRepDto> deleteLikeMemoryStar(@PathVariable Long memoryId) {
        MemoryStarRepDto memoryStarRepDto = memoryStarService.deleteLike(memoryId);
        return ResponseEntity.status(HttpStatus.OK).body(memoryStarRepDto);
    }
}
