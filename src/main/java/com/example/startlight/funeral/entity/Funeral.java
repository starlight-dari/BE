package com.example.startlight.funeral.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Funeral")
public class Funeral {
    @Id
    private Long id;

    private String phone;

    private String address;

    private String name;
}
