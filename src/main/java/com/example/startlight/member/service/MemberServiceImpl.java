package com.example.startlight.member.service;

import com.example.startlight.kakao.dto.KakaoUserCreateDto;
import com.example.startlight.member.dao.MemberDao;
import com.example.startlight.member.dto.MemberDto;
import com.example.startlight.member.entity.Member;
import com.example.startlight.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberDao memberDao;
    private final MemberRepository memberRepository;
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

    @Override
    public MemberDto loginMember(KakaoUserCreateDto kakaoUserCreateDto) {
        Optional<Member> memberOptional = memberRepository.findById(kakaoUserCreateDto.getId());
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            return MemberDto.toDto(member);
        }
        else {
            Member member = Member.builder().member_id(kakaoUserCreateDto.getId())
                    .kk_nickname(kakaoUserCreateDto.getNickName())
                    .profile_img(kakaoUserCreateDto.getProfileImageUrl()).build();
            memberDao.createMember(member);
            return MemberDto.toDto(member);
        }
    }
}
