package com.example.startlight.memComment.dao;

import com.example.startlight.memComment.entity.MemComment;
import com.example.startlight.memComment.repository.MemCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemCommentDao {
    private final MemCommentRepository memCommentRepository;

    public MemComment create(MemComment memComment) {
        return memCommentRepository.save(memComment);
    }
}
