package com.example.startlight.memComment.repository;

import com.example.startlight.memComment.entity.MemComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemCommentRepository extends JpaRepository<MemComment, Long> {
}
