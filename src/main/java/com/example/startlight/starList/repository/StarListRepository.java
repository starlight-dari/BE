package com.example.startlight.starList.repository;

import com.example.startlight.starList.entity.StarList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarListRepository extends JpaRepository<StarList, Integer> {
}
