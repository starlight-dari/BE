package com.example.startlight.memoryStar.entity;

import com.example.startlight.memoryStar.dto.MemoryStarReqDto;
import com.example.startlight.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="MemoryStar")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false)
    private String img_url;

    public static MemoryStar toEntity(MemoryStarReqDto dto, Pet pet) {
        return MemoryStar.builder()
                .pet(pet)
                .x_star(dto.getX_star())
                .y_star(dto.getY_star())
                .name(dto.getName())
                .ctg_situation(dto.getCtg_situation())
                .ctg_feeling(dto.getCtg_feeling())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .img_url(dto.getImg_url())
                .build();
    }
}
