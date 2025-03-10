package com.example.startlight.starList.entity;

import com.example.startlight.memoryStar.dto.MemoryStarUpdateDto;
import com.example.startlight.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="StarList")
public class StarList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long star_id;

    private Integer index_id;

    @ManyToOne
    private Pet pet;

    @Column(nullable = false)
    private Integer x_star;

    @Column(nullable = false)
    private Integer y_star;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private Boolean written = false;

    public void updateStarList() {
        this.written = Boolean.TRUE;
    }
}
