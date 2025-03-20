package com.example.startlight.memoryAlbum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LetterGenerateRepDto {
    @JsonProperty("letter")
    String letter;
    @JsonProperty("title")
    String title;
}
