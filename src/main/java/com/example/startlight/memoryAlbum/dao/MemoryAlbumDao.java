package com.example.startlight.memoryAlbum.dao;

import com.example.startlight.memoryAlbum.entity.MemoryAlbum;
import com.example.startlight.memoryAlbum.repository.MemoryAlbumRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemoryAlbumDao {
    private final MemoryAlbumRepository memoryAlbumRepository;

    public MemoryAlbum createMemoryAlbum(MemoryAlbum memoryAlbum) {
        return memoryAlbumRepository.save(memoryAlbum);
    }

    public List<MemoryAlbum> findByPetId(Long petId) {
        return memoryAlbumRepository.findAllByPetId(petId);
    }

    public MemoryAlbum findById(Long id) {
        return memoryAlbumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album not found with id: " + id));
    }
}
