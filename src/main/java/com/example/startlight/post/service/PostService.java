package com.example.startlight.post.service;

import com.example.startlight.member.dao.MemberDao;
import com.example.startlight.member.entity.Member;
import com.example.startlight.post.dao.PostDao;
import com.example.startlight.post.dto.PostRequestDto;
import com.example.startlight.post.dto.PostResponseDto;
import com.example.startlight.post.entity.Post;
import com.example.startlight.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostDao postDao;
    private final MemberDao memberDao;
    private final PostMapper postMapper = PostMapper.INSTANCE;

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        //TODO
        // Long userId = UserUtil.getCurrentUserId();
        Long userId = 3879188713L;
        Member member = memberDao.selectMember(userId);
        Post post = Post.toEntity(postRequestDto, member);
        Post createdPost = postDao.createPost(post);
        return postMapper.toPostResponseDto(createdPost);
    }
}
