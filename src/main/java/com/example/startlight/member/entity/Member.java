package com.example.startlight.member.entity;

import com.example.startlight.pet.entity.Pet;
import com.example.startlight.post.entity.Post;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long member_id;

    @Column(nullable = false)
    private String kk_nickname;

    @Column(nullable = false)
    private String profile_img;

    private String email;

    private String st_nickname;

    @OneToMany
    private List<Post> posts;

    @OneToMany
    private List<Pet> pets;
}
