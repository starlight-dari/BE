package com.example.startlight.post.mapper;

import com.example.startlight.member.entity.Member;
import com.example.startlight.post.dto.PostRequestDto;
import com.example.startlight.post.dto.PostResponseDto;
import com.example.startlight.post.entity.Category;
import com.example.startlight.post.entity.Post;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.PostMapping;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "member.st_nickname", target = "writer")
    PostResponseDto toPostResponseDto(Post post);
}
