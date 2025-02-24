package com.example.startlight.memComment.controller;

import com.example.startlight.memComment.dto.MemCommentRepDto;
import com.example.startlight.memComment.dto.MemCommentReqDto;
import com.example.startlight.memComment.service.MemCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memory/comment")
@RequiredArgsConstructor
public class MemCommentController {
    private final MemCommentService memCommentService;

    @PostMapping("/create")
    public ResponseEntity<MemCommentRepDto> createMemComment(@RequestBody MemCommentReqDto memCommentReqDto) {
        MemCommentRepDto memCommentRepDto = memCommentService.saveMemComment(memCommentReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(memCommentRepDto);
    }
}
