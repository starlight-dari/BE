package com.example.startlight.chatSession.entity;

import com.example.startlight.member.entity.Member;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "chatSession")
public class ChatSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chatss_id;

    @ManyToOne
    private Member member;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
