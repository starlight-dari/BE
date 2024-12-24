package com.example.startlight.chatMessage.entity;

import com.example.startlight.chatSession.entity.ChatSession;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "ChatMessage")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chatmsg_id;

    @ManyToOne
    private ChatSession chatSession;

    @Column(nullable = false)
    private Boolean memberSent;

    @Column(nullable = false)
    private String message;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
