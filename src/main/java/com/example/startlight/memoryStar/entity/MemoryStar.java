package com.example.startlight.memoryStar.entity;

import com.example.startlight.memoryStar.dto.MemoryStarReqDto;
import com.example.startlight.memoryStar.dto.MemoryStarUpdateDto;
import com.example.startlight.starList.entity.StarList;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="MemoryStar")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MemoryStar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memory_id;

    @OneToOne
    @JoinColumn(name = "star_id", nullable = true) // ✅ NULL 허용
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private StarList starList;

    @Column(nullable = false)
    private String name;

    private ActivityCtg activityCtg;

    private EmotionCtg emotionCtg;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @ColumnDefault("false")
    private Boolean shared;

    private Long likes;

    @Setter
    private String img_url;

    public void updateMemoryStar(MemoryStarUpdateDto dto) {
        this.name = dto.getName();
        this.activityCtg = dto.getActivityCtg();
        this.emotionCtg = dto.getEmotionCtg();
        this.content = dto.getContent();
        this.shared = dto.getShared();
    }

}
