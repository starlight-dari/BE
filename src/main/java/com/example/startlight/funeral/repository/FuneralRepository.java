package com.example.startlight.funeral.repository;

import com.example.startlight.funeral.entity.Funeral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuneralRepository extends JpaRepository<Funeral, Long> {
}
