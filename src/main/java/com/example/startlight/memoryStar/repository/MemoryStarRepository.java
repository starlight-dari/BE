package com.example.startlight.memoryStar.repository;

import com.example.startlight.memoryStar.entity.MemoryStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemoryStarRepository extends JpaRepository<MemoryStar, Long> {
    List<MemoryStar> findBySharedTrue();

    @Query("select m from MemoryStar m where m.writer_id = :userId")
    List<MemoryStar> findAllByWriterId(@Param("userId") Long userId);
}
