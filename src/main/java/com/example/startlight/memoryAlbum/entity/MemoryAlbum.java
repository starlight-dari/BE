package com.example.startlight.memoryAlbum.entity;

import com.example.startlight.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "MemoryAlbum")
public class MemoryAlbum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long albumId;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Column(nullable = false)
    private String content;

    private String imageUrl;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder.Default
    private Boolean opened = false;
}
