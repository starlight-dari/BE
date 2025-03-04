package com.example.startlight.post.entity;

import com.example.startlight.member.entity.Member;
import com.example.startlight.post.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long post_id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private Long funeral_id;

    @Column(nullable = false)
    private Long report;

    public static Post toEntity(PostRequestDto postRequestDto, Member member) {
        return Post.builder()
                .member(member)
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .category(postRequestDto.getCategory())
                .funeral_id(postRequestDto.getFuneral_id())
                .report(0L).build();
    }
}
