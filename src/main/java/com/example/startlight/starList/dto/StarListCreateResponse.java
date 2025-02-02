package com.example.startlight.starList.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StarListCreateResponse {
    private Long petId;
    private List<StarListRepDto> starList;
}
