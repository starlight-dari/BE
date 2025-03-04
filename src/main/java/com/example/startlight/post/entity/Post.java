package com.example.startlight.post.entity;

import com.example.startlight.member.entity.Member;
import com.example.startlight.post.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    private Long funeral_id;

    @Column(nullable = false)
    private Long report;

    @Setter
    private String img_url;

    @CreatedDate
    private LocalDateTime createdAt;

    public static Post toEntity(PostRequestDto postRequestDto, Member member) {
        if(postRequestDto.getFuneral_id() != null) {
            return Post.builder()
                    .member(member)
                    .title(postRequestDto.getTitle())
                    .content(postRequestDto.getContent())
                    .category(postRequestDto.getCategory())
                    .funeral_id(postRequestDto.getFuneral_id())
                    .report(0L).build();
        }
        else {
            return Post.builder()
                    .member(member)
                    .title(postRequestDto.getTitle())
                    .content(postRequestDto.getContent())
                    .category(postRequestDto.getCategory())
                    .report(0L).build();
        }

    }
}
