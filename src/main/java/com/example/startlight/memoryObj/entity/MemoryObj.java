package com.example.startlight.memoryObj.entity;

import com.example.startlight.pet.entity.Pet;
import jakarta.persistence.*;

@Entity
@Table(name = "memoryObj")
public class MemoryObj {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long obj_id;

    @ManyToOne
    private Pet pet;

    @Column(nullable = false)
    private String img;

    @Column(nullable = false)
    private String name;

    private String content;
}
