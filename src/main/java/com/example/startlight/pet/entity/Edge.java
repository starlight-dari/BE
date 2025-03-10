package com.example.startlight.pet.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Edge {
    private Integer startPoint;
    private Integer endPoint;
}
