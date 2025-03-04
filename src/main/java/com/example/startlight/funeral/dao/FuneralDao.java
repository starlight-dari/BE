package com.example.startlight.funeral.dao;

import com.example.startlight.funeral.entity.Funeral;
import com.example.startlight.funeral.repository.FuneralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class FuneralDao {
    private final FuneralRepository funeralRepository;

    public Funeral selectById(Long id) {
        return funeralRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Funeral not found with id: " + id));
    }
}
