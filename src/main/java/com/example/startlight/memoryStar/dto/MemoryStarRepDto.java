package com.example.startlight.memoryStar.dto;

import com.example.startlight.memoryStar.entity.MemoryStar;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MemoryStarRepDto {
    private Long star_id;

    private Long x_star;

    private Long y_star;

    private String name;

    private String ctg_situation;

    private String ctg_feeling;

    private String content;

    private LocalDateTime createdAt;

    private Boolean shared;

    private Long likes;

    private String img_url;

    public static MemoryStarRepDto toDto(MemoryStar memoryStar) {
        return  MemoryStarRepDto.builder()
                .star_id(memoryStar.getStar_id())
                .x_star(memoryStar.getX_star())
                .y_star(memoryStar.getY_star())
                .name(memoryStar.getName())
                .ctg_situation(memoryStar.getCtg_situation())
                .ctg_feeling(memoryStar.getCtg_feeling())
                .content(memoryStar.getContent())
                .createdAt(memoryStar.getCreatedAt())
                .shared(memoryStar.getShared())
                .likes(memoryStar.getLikes())
                .img_url(memoryStar.getImg_url())
                .build();
    }
}
