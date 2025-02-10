package com.example.startlight.memoryStar.service;

import com.example.startlight.memoryStar.dao.MemoryStarDao;
import com.example.startlight.memoryStar.dto.MemoryStarRepDto;
import com.example.startlight.memoryStar.dto.MemoryStarReqDto;
import com.example.startlight.memoryStar.dto.MemoryStarUpdateDto;
import com.example.startlight.memoryStar.entity.MemoryStar;
import com.example.startlight.memoryStar.mapper.MemoryStarMapper;
import com.example.startlight.starList.dao.StarListDao;
import com.example.startlight.starList.entity.StarList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemoryStarService {
    private final MemoryStarDao memoryStarDao;
    private final StarListDao starListDao;
    private final MemoryStarMapper mapper = MemoryStarMapper.INSTANCE;

    public MemoryStarRepDto selectStarById(Long id) {
        MemoryStar memoryStar = memoryStarDao.selectMemoryStarById(id);
        return mapper.toDto(memoryStar);
    }

    public MemoryStarRepDto createMemoryStar(MemoryStarReqDto memoryStarReqDto) {
        StarList starListById = starListDao.findStarListById(memoryStarReqDto.getStar_id());
        MemoryStar memoryStar = mapper.toEntity(memoryStarReqDto, starListById);
        MemoryStar createdStar = memoryStarDao.createMemoryStar(memoryStar);
        starListDao.updateStarWritten(memoryStarReqDto.getStar_id());
        return mapper.toDto(createdStar);
    }

    public MemoryStarRepDto updateMemoryStar(MemoryStarUpdateDto memoryStarUpdateDto) {
        MemoryStar memoryStar = memoryStarDao.updateMemoryStar(memoryStarUpdateDto);
        return mapper.toDto(memoryStar);
    }

    public void deleteMemoryStar(Long id) {
        memoryStarDao.deleteMemoryStarById(id);
    }
}
