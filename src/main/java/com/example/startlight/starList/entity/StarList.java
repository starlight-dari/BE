package com.example.startlight.starList.entity;

import com.example.startlight.pet.entity.Pet;
import jakarta.persistence.*;

@Entity
@Table(name="StarList")
public class StarList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long starList_id;

    @ManyToOne
    private Pet pet;

    @Column(nullable = false)
    private Long x_star;

    @Column(nullable = false)
    private Long y_star;
}
