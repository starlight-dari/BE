package com.example.startlight.member.dao;

import com.example.startlight.member.entity.Member;
import com.example.startlight.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberDaoImpl implements MemberDao{

    private final MemberRepository memberRepository;
    @Override
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member selectMember(Long id) {
        Optional<Member> selectedMember = memberRepository.findById(id);
        if(selectedMember.isPresent()) {
            return selectedMember.get();
        }
        throw new NoSuchElementException("Member not found with id: " + id);
    }

    @Override
    public Member selectMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findMemberByEmail(email);
        if(member.isPresent()) {
            return member.get();
        }
        throw new NoSuchElementException("Member not found with email: " + email);
    }

    @Override
    @Transactional
    public Member updateMemberName(Long id, String nickname) {
        Member member = selectMember(id);
        member.setSt_nickname(nickname);
        return member;
    }

    @Override
    public void deleteMember(Long id) {
        Member member = selectMember(id);
        memberRepository.delete(member);
    }
}
