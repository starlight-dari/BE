package com.example.startlight.starList.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StarListReqDto {
    private Long x_star;
    private Long y_star;
}
