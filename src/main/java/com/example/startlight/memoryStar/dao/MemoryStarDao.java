package com.example.startlight.memoryStar.dao;

import com.example.startlight.memoryStar.repository.MemoryStarRepository;
import com.example.startlight.memoryStar.entity.MemoryStar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemoryStarDao {

    private final MemoryStarRepository memoryStarRepository;

    public MemoryStar createMemoryStar(MemoryStar memoryStar) {
        return memoryStarRepository.save(memoryStar);
    }

    public MemoryStar selectMemoryStarById(Long id) {
        Optional<MemoryStar> memoryStar = memoryStarRepository.findById(id);
        if (memoryStar.isPresent()) {
            return memoryStar.get();
        }
        throw new NoSuchElementException("Memory Star not found with id: " + id);
    }
}
