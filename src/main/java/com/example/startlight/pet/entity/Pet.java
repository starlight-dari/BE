package com.example.startlight.pet.entity;

import com.example.startlight.member.dao.MemberDao;
import com.example.startlight.member.entity.Member;
import com.example.startlight.pet.dto.PetDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Pet")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pet_id;

    @ManyToOne
    private Member member;

    private String pet_img;

    @Column(nullable = false)
    private String pet_name;

    private String species;

    private String birth_date;

    private String death_date;

    public static Pet toEntity(PetDto dto, MemberDao memberDao) {
        Member member = memberDao.selectMemberByEmail(dto.getMember_email());
        if (member == null) {
            throw new IllegalArgumentException("Member not found for email: " + dto.getMember_email());
        }

        // Pet 엔티티 생성
        return Pet.builder()
                .member(member)
                .pet_img(dto.getPet_img())
                .pet_name(dto.getPet_name())
                .species(dto.getSpecies())
                .birth_date(dto.getBirth_date())
                .death_date(dto.getDeath_date())
                .build();
    }
}
