package com.example.startlight.starList.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StarListRepDto {
    private Long star_id;
    private Long x_star;
    private Long y_star;
    private Boolean written;
}
