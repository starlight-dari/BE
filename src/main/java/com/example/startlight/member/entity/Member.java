package com.example.startlight.member.entity;

import com.example.startlight.member.dto.MemberDto;
import com.example.startlight.pet.entity.Pet;
import com.example.startlight.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Member")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long member_id;

    @Column(nullable = false)
    private String kk_nickname;

    @Column(nullable = false)
    private String profile_img;

    @Column(nullable = false)
    private String email;

    private String st_nickname;

    @OneToMany
    private List<Post> posts;

    @OneToMany
    private List<Pet> pets;

    public static Member toEntity(MemberDto dto) {
        return Member.builder()
                .kk_nickname(dto.getKk_nickname())
                .profile_img(dto.getProfile_img())
                .email(dto.getEmail())
                .st_nickname(dto.getSt_nickname()).build();
    }
}
