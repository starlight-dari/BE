package com.example.startlight.memComment.entity;

import com.example.startlight.memoryStar.entity.MemoryStar;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="MemComment")
public class MemComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long comment_id;

    @ManyToOne
    @JoinColumn(name = "memory_id")
    private MemoryStar memoryStar;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long writer_id;

    public void updateContent(String content) {
        this.content = content;
    }
}
