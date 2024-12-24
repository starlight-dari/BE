package com.example.startlight.memComment.entity;

import com.example.startlight.memoryStar.entity.MemoryStar;
import jakarta.persistence.*;

@Entity
@Table(name="MemComment")
public class MemComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long comment_id;

    @ManyToOne
    private MemoryStar memoryStar;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long writer_id;
}
