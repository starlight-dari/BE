package com.example.startlight.post.entity;

import com.example.startlight.member.entity.Member;
import jakarta.persistence.*;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long post_id;

    @ManyToOne
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
}
