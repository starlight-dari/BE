package com.example.startlight.memComment.mapper;

import com.example.startlight.memComment.dto.MemCommentRepDto;
import com.example.startlight.memComment.entity.MemComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemCommentMapper {
    MemCommentMapper INSTANCE = Mappers.getMapper(MemCommentMapper.class);
    // ✅ Entity → DTO 변환 (메모리 ID는 String으로 변환)
//    @Mapping(source = "memoryStar.memory_id", target = "memory_id")
//    MemCommentRepDto toDto(MemComment memComment);
}
