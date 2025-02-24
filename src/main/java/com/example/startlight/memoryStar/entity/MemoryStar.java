package com.example.startlight.memoryStar.entity;

import com.example.startlight.memComment.entity.MemComment;
import com.example.startlight.memLike.entity.MemLike;
import com.example.startlight.memoryStar.dto.MemoryStarUpdateDto;
import com.example.startlight.starList.entity.StarList;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

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

    @Builder.Default
    @ColumnDefault("false")
    @Column(nullable = false)
    private Boolean shared = false;  // 기본값 설정

    @Builder.Default
    @ColumnDefault("0")
    @Column(nullable = false)
    private Long likes = 0L;  // 기본값 설정

    @Builder.Default
    @ColumnDefault("0")
    @Column(nullable = false)
    private Long commentNumber = 0L;  // 기본값 설정

    @Setter
    private String img_url;

    @OneToMany
    private List<MemComment> memComments;

    @OneToMany
    private List<MemLike> memLikes;
    
    public void updateMemoryStar(MemoryStarUpdateDto dto) {
        this.name = dto.getName();
        this.activityCtg = dto.getActivityCtg();
        this.emotionCtg = dto.getEmotionCtg();
        this.content = dto.getContent();
        this.shared = dto.getShared();
    }

    public void createLike() {
        this.likes++;
    }

    public void deleteLike() {
        this.likes--;
    }

    public void createComment() {
        this.commentNumber++;
    }

    public void deleteComment() {
        this.commentNumber--;
    }
}
