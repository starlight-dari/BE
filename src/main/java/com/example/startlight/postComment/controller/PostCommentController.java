package com.example.startlight.postComment.controller;

import com.example.startlight.postComment.dto.PostCommentRepDto;
import com.example.startlight.postComment.dto.PostCommentReqDto;
import com.example.startlight.postComment.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/comment")
public class PostCommentController {
    private final PostCommentService postCommentService;

    @PostMapping("/create")
    public ResponseEntity<PostCommentRepDto> postComment(@RequestBody PostCommentReqDto postCommentReqDto) {
        PostCommentRepDto postComment = postCommentService.createPostComment(postCommentReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(postComment);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteComment(@RequestParam Long commentId) {
        postCommentService.deletePostComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted comment");
    }
}
