package com.example.startlight.memoryStar.dto;

import com.example.startlight.memoryStar.entity.ActivityCtg;
import com.example.startlight.memoryStar.entity.EmotionCtg;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemoryStarReqDto {
    private Long star_id;

    private String name;

    private ActivityCtg activityCtg;

    private EmotionCtg emotionCtg;

    private String content;

    private String img_url;

    private Boolean shared;

}
