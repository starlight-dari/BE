package com.example.startlight.post.dao;

import com.example.startlight.post.entity.Post;
import com.example.startlight.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostDao {
    private final PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }
}
