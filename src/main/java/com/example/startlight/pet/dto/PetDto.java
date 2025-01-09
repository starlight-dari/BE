package com.example.startlight.pet.dto;

import com.example.startlight.member.dto.MemberDto;
import com.example.startlight.member.entity.Member;
import com.example.startlight.pet.entity.Pet;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PetDto {

    private String member_email;

    private String pet_img;

    private String pet_name;

    private String species;

    private String birth_date;

    private String death_date;

    public static PetDto toDto(Pet pet) {
        return PetDto.builder()
                .member_email(pet.getMember().getEmail())
                .pet_img(pet.getPet_img())
                .pet_name(pet.getPet_name())
                .species(pet.getSpecies())
                .birth_date(pet.getBirth_date())
                .death_date(pet.getDeath_date())
                .build();
    }
}
