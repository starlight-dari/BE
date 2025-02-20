package com.example.startlight.memoryStar.dao;

import com.example.startlight.memoryStar.dto.MemoryStarUpdateDto;
import com.example.startlight.memoryStar.repository.MemoryStarRepository;
import com.example.startlight.memoryStar.entity.MemoryStar;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Transactional
    public MemoryStar updateMemoryStar(MemoryStarUpdateDto memoryStarUpdateDto) {
        Optional<MemoryStar> starOptional = memoryStarRepository.findById(memoryStarUpdateDto.getMemory_id());
        if(starOptional.isPresent()) {
            MemoryStar memoryStar = starOptional.get();
            memoryStar.updateMemoryStar(memoryStarUpdateDto);
            return memoryStar;
        }
        throw new NoSuchElementException("Memory Star not found with id: " + memoryStarUpdateDto.getMemory_id());
    }

    public void deleteMemoryStarById(Long id) {
        memoryStarRepository.deleteById(id);
    }

    public List<MemoryStar> getAllPublicMemoryStar() {
        return memoryStarRepository.findAllByShared(true);
    }
}
