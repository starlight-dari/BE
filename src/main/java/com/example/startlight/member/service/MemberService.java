package com.example.startlight.member.service;

import com.example.startlight.member.dto.MemberDto;

public interface MemberService {
    MemberDto createMember(MemberDto memberDto);

    MemberDto selectMember(Long id);

    MemberDto updateMemberName(Long id, String nickname);

    void deleteMember(Long id);
}
