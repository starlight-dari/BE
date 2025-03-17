package com.example.startlight.memoryAlbum.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ImgRequestDto {
    List<String> images;
}
