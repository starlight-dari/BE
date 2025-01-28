package com.example.startlight.memoryStar.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemoryStarReqDto {
    private Long pet_id;

    private Long x_star;

    private Long y_star;

    private String name;

    private String ctg_situation;

    private String ctg_feeling;

    private String content;

    private String img_url;

}
