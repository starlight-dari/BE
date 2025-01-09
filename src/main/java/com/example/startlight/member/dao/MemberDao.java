package com.example.startlight.member.dao;

import com.example.startlight.member.entity.Member;

public interface MemberDao {
    Member createMember(Member member);

    Member selectMember(Long id);

    Member selectMemberByEmail(String email);

    Member updateMemberName(Long id, String nickname);

    void deleteMember(Long id);
}
