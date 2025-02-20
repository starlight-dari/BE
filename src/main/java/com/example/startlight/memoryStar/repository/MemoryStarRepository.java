package com.example.startlight.memoryStar.repository;

import com.example.startlight.memoryStar.entity.MemoryStar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoryStarRepository extends JpaRepository<MemoryStar, Long> {
    List<MemoryStar> findAllByShared(Boolean shared);
}
