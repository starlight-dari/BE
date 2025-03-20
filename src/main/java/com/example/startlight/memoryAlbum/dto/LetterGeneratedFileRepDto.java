package com.example.startlight.memoryAlbum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LetterGeneratedFileRepDto {
    @JsonProperty("images")
    List<String> images;
    @JsonProperty("letter")
    String letter;
    @JsonProperty("title")
    String title;
}
