package com.example.startlight.memoryStar.entity;

import com.example.startlight.pet.entity.Pet;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name="MemoryStar")
public class MemoryStar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long star_id;

    @ManyToOne
    private Pet pet;

    @Column(nullable = false)
    private Long x_star;

    @Column(nullable = false)
    private Long y_star;

    @Column(nullable = false)
    private String name;

    private String ctg_situation;

    private String ctg_feeling;

    private String content;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ColumnDefault("false")
    private Boolean shared;

    private Long likes;

}
