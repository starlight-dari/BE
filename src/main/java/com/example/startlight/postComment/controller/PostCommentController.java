package com.example.startlight.postComment.controller;

import com.example.startlight.postComment.dto.PostCommentRepDto;
import com.example.startlight.postComment.dto.PostCommentReqDto;
import com.example.startlight.postComment.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/comment")
public class PostCommentController {
    private final PostCommentService postCommentService;

    @PostMapping("/create")
    public ResponseEntity<List<PostCommentRepDto>> postComment(@RequestBody PostCommentReqDto postCommentReqDto) {
        List<PostCommentRepDto> postComment = postCommentService.createPostComment(postCommentReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(postComment);
    }
}
