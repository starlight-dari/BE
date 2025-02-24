package com.example.startlight.memoryStar.dto;

import com.example.startlight.memComment.dto.MemCommentRepDto;
import com.example.startlight.memoryStar.entity.ActivityCtg;
import com.example.startlight.memoryStar.entity.EmotionCtg;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class MemoryStarRepWithComDto {
    private Long memory_id;

    private Long star_id;

    private String name;

    private ActivityCtg activityCtg;

    private EmotionCtg emotionCtg;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    private Boolean shared;

    private Long likes;

    private Long commentNumber;

    private Boolean isLiked;

    private String img_url;

    private List<MemCommentRepDto> memComments;
}
