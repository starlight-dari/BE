package com.example.startlight.post.controller;

import com.example.startlight.post.dto.PostRequestDto;
import com.example.startlight.post.dto.PostDetailedRepDto;
import com.example.startlight.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostDetailedRepDto> create(@ModelAttribute PostRequestDto postRequestDto) throws IOException {
        PostDetailedRepDto post = postService.createPost(postRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }
}
