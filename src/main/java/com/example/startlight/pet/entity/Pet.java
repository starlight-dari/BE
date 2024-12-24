package com.example.startlight.pet.entity;

import com.example.startlight.member.entity.Member;
import jakarta.persistence.*;

@Entity
@Table(name = "Pet")
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

}
