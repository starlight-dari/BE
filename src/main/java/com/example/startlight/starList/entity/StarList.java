package com.example.startlight.starList.entity;

import com.example.startlight.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
