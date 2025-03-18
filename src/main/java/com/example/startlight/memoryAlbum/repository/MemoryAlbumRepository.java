package com.example.startlight.memoryAlbum.repository;

import com.example.startlight.memoryAlbum.entity.MemoryAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoryAlbumRepository extends JpaRepository<MemoryAlbum, Long> {
    public List<MemoryAlbum>
}
