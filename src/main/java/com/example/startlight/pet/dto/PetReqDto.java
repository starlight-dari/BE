package com.example.startlight.pet.dto;

import com.example.startlight.pet.entity.Gender;
import com.example.startlight.pet.entity.Personality;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetReqDto {

    private String pet_name;

    private String species;

    private Gender gender;

    private String birth_date;

    private String death_date;

    private Personality personality;
}
