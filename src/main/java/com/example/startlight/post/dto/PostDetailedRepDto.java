package com.example.startlight.post.dto;

import com.example.startlight.funeral.dao.FuneralDao;
import com.example.startlight.funeral.entity.Funeral;
import com.example.startlight.funeral.repository.FuneralRepository;
import com.example.startlight.post.entity.Category;
import com.example.startlight.post.entity.Post;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostDetailedRepDto {
    private Long post_id;
    private String writer;
    private String title;
    private String content;
    private Category category;
    private Long report;

    @Nullable
    private String img_url;

    @Nullable
    private Funeral funeral;

    public static PostDetailedRepDto toDto(Post post, FuneralDao funeralDao) {
        if (post.getFuneral_id() == null) {
            return PostDetailedRepDto.builder()
                    .post_id(post.getPost_id())
                    .writer(post.getMember().getSt_nickname())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .category(post.getCategory())
                    .report(post.getReport())
                    .img_url(post.getImg_url())
                    .build();
        }
        else {
            return PostDetailedRepDto.builder()
                    .post_id(post.getPost_id())
                    .writer(post.getMember().getSt_nickname())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .category(post.getCategory())
                    .report(post.getReport())
                    .img_url(post.getImg_url())
                    .funeral(funeralDao.selectById(post.getFuneral_id()))
                    .build();
        }
    }
}
