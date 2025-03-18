package com.example.startlight.memoryAlbum.dao;

import com.example.startlight.memoryAlbum.entity.MemoryAlbum;
import com.example.startlight.memoryAlbum.repository.MemoryAlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
