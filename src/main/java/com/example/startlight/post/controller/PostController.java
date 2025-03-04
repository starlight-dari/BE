package com.example.startlight.post.controller;

import com.example.startlight.post.dto.PostRequestDto;
import com.example.startlight.post.dto.PostDetailedRepDto;
import com.example.startlight.post.dto.PostResponseDto;
import com.example.startlight.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/get")
    public ResponseEntity<PostDetailedRepDto> get(@RequestParam Long id) throws IOException {
        PostDetailedRepDto post = postService.getPost(id);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PostResponseDto>> getAll(@RequestParam String category){
        List<PostResponseDto> posts = postService.getAllPosts(category);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }
}
