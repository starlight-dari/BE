package com.example.startlight.post.dao;

import com.example.startlight.post.dto.PostRequestDto;
import com.example.startlight.post.entity.Post;
import com.example.startlight.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostDao {
    private final PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post findPostById(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            return postOptional.get();
        }
        throw new NoSuchElementException("Post not found with id: " + id);
    }
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }
}
