package com.example.startlight.memComment.service;

import com.example.startlight.memComment.dao.MemCommentDao;
import com.example.startlight.memComment.dto.MemCommentRepDto;
import com.example.startlight.memComment.dto.MemCommentReqDto;
import com.example.startlight.memComment.dto.MemCommentUpdateReqDto;
import com.example.startlight.memComment.entity.MemComment;
import com.example.startlight.memComment.mapper.MemCommentMapper;
import com.example.startlight.memoryStar.dao.MemoryStarDao;
import com.example.startlight.memoryStar.entity.MemoryStar;
import com.example.startlight.memoryStar.mapper.MemoryStarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemCommentService {
    private final MemCommentDao memCommentDao;
    private final MemCommentMapper mapper = MemCommentMapper.INSTANCE;
    private final MemoryStarDao memoryStarDao;

    public MemCommentRepDto saveMemComment(MemCommentReqDto memCommentReqDto) {
        //TODO
        // Long userId = UserUtil.getCurrentUserId();
        Long userId = 3879188713L;
        MemoryStar memoryStar = memoryStarDao.selectMemoryStarById(memCommentReqDto.getMemory_id());
        MemComment memComment = MemComment.builder()
                .content(memCommentReqDto.getContent())
                .writer_id(userId)
                .memoryStar(memoryStar)
                .build();
        MemComment memComment1 = memCommentDao.create(memComment);
        return mapper.toDto(memComment1);
    }

    public MemCommentRepDto updateMemComment(MemCommentUpdateReqDto dto) {
        //TODO
        // Long userId = UserUtil.getCurrentUserId();
        Long userId = 3879188713L;
        MemComment memComment = memCommentDao.update(dto.getComment_id(), userId, dto.getContent());
        return mapper.toDto(memComment);
    }

    public void deleteMemComment(Long comment_id) {
        //TODO
        // Long userId = UserUtil.getCurrentUserId();
        Long userId = 3879188713L;
        memCommentDao.delete(userId, comment_id);
    }

    public List<MemCommentRepDto> findAllByMemoryId(Long memory_id) {
        List<MemComment> allByMemoryId = memCommentDao.findAllByMemoryId(memory_id);
        return allByMemoryId.stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
