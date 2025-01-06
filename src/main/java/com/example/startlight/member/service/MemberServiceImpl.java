package com.example.startlight.member.service;

import com.example.startlight.member.dao.MemberDao;
import com.example.startlight.member.dto.MemberDto;
import com.example.startlight.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberDao memberDao;
    @Override
    public MemberDto createMember(MemberDto memberDto) {
        Member member = memberDao.createMember(Member.toEntity(memberDto));
        return MemberDto.toDto(member);
    }

    @Override
    public MemberDto selectMember(Long id) {
        Member member = memberDao.selectMember(id);
        return MemberDto.toDto(member);
    }

    @Override
    public MemberDto updateMemberName(Long id, String nickname) {
        Member member = memberDao.updateMemberName(id, nickname);
        return MemberDto.toDto(member);
    }

    @Override
    public void deleteMember(Long id) {
        memberDao.deleteMember(id);
    }
}
