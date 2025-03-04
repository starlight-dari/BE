package com.example.startlight.post.dto;

import com.example.startlight.post.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostRequestDto {
    private String title;
    private String content;
    private Category category;
    private Long funeral_id;
}
