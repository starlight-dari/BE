package com.example.startlight.memoryStar.service;

import com.example.startlight.member.dao.MemberDao;
import com.example.startlight.member.service.MemberService;
import com.example.startlight.memoryStar.dao.MemoryStarDao;
import com.example.startlight.memoryStar.dto.MemoryStarRepDto;
import com.example.startlight.memoryStar.dto.MemoryStarReqDto;
import com.example.startlight.memoryStar.dto.MemoryStarSimpleRepDto;
import com.example.startlight.memoryStar.dto.MemoryStarUpdateDto;
import com.example.startlight.memoryStar.entity.MemoryStar;
import com.example.startlight.memoryStar.mapper.MemoryStarMapper;
import com.example.startlight.s3.service.S3Service;
import com.example.startlight.starList.dao.StarListDao;
import com.example.startlight.starList.entity.StarList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemoryStarService {
    private final MemoryStarDao memoryStarDao;
    private final StarListDao starListDao;
    private final MemberService memberService;
    private final S3Service s3Service;
    private final MemoryStarMapper mapper = MemoryStarMapper.INSTANCE;

    public MemoryStarRepDto selectStarById(Long id) {
        MemoryStar memoryStar = memoryStarDao.selectMemoryStarById(id);
        return mapper.toDto(memoryStar);
    }

    public MemoryStarRepDto createMemoryStar(MemoryStarReqDto memoryStarReqDto) throws IOException {
        StarList starListById = starListDao.findStarListById(memoryStarReqDto.getStar_id());
        MemoryStar memoryStar = mapper.toEntity(memoryStarReqDto, starListById);
        MemoryStar createdStar = memoryStarDao.createMemoryStar(memoryStar);
        String memoryImgUrls = s3Service.uploadMemoryImg(memoryStarReqDto.getImg_url(), createdStar.getMemory_id());
        createdStar.setImg_url(memoryImgUrls);
        starListDao.updateStarWritten(memoryStarReqDto.getStar_id());
        memberService.updateMemberMemory();
        return mapper.toDto(createdStar);
    }

    public MemoryStarRepDto updateMemoryStar(MemoryStarUpdateDto memoryStarUpdateDto) throws IOException {
        MemoryStar memoryStar = memoryStarDao.updateMemoryStar(memoryStarUpdateDto);
        String uploadMemoryImg = s3Service.uploadMemoryImg(memoryStarUpdateDto.getImg_url(), memoryStarUpdateDto.getMemory_id());
        memoryStar.setImg_url(uploadMemoryImg);
        return mapper.toDto(memoryStar);
    }

    public void deleteMemoryStar(Long id) {
        memoryStarDao.deleteMemoryStarById(id);
    }

    public List<MemoryStarSimpleRepDto> findAllPublicMemoryStar() {
        List<MemoryStar> allPublicMemoryStar = memoryStarDao.getAllPublicMemoryStar();
        return mapper.toSimpleRepDtoList(allPublicMemoryStar);
    }
}
