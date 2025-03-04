package com.example.startlight.post.service;

import com.example.startlight.funeral.dao.FuneralDao;
import com.example.startlight.funeral.entity.Funeral;
import com.example.startlight.member.dao.MemberDao;
import com.example.startlight.member.entity.Member;
import com.example.startlight.post.dao.PostDao;
import com.example.startlight.post.dto.PostRequestDto;
import com.example.startlight.post.dto.PostDetailedRepDto;
import com.example.startlight.post.dto.PostResponseDto;
import com.example.startlight.post.entity.Post;
import com.example.startlight.s3.service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostDao postDao;
    private final MemberDao memberDao;
    private final FuneralDao funeralDao;
    private final S3Service s3Service;

    public PostDetailedRepDto createPost(PostRequestDto postRequestDto) throws IOException {
        //TODO
        // Long userId = UserUtil.getCurrentUserId();
        Long userId = 3879188713L;
        Member member = memberDao.selectMember(userId);
        Post post = Post.toEntity(postRequestDto, member);
        Post createdPost = postDao.createPost(post);
        if (postRequestDto.getImage() != null) {
            String postImgUrl = s3Service.uploadPostImg(postRequestDto.getImage(), createdPost.getPost_id());
            createdPost.setImg_url(postImgUrl);
        }
        return PostDetailedRepDto.toDto(createdPost, funeralDao);
    }

    public PostDetailedRepDto getPost(Long postId) {
        Post postById = postDao.findPostById(postId);
        return PostDetailedRepDto.toDto(postById, funeralDao);
    }

    public List<PostResponseDto> getAllPosts() {
        List<Post> allPost = postDao.findAllPost();
        return allPost.stream()
                .map(PostResponseDto::toResponseDto)  // Post → PostRequestDto 변환
                .collect(Collectors.toList());
    }
}
