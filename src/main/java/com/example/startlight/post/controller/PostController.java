package com.example.startlight.post.controller;

import com.example.startlight.post.dto.PostRequestDto;
import com.example.startlight.post.dto.PostResponseDto;
import com.example.startlight.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostResponseDto> create(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto post = postService.createPost(postRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }
}
