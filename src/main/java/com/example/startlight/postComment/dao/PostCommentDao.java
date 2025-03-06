package com.example.startlight.postComment.dao;

import com.example.startlight.postComment.entity.PostComment;
import com.example.startlight.postComment.repository.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostCommentDao {
    private final PostCommentRepository postCommentRepository;

    public List<PostComment> createPostCommentAndGetAll(Long postId, PostComment postComment) {
        postCommentRepository.save(postComment);
        return postCommentRepository.findAllByPostId(postId);
    }
}
