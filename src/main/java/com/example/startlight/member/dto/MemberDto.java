package com.example.startlight.member.dto;

import com.example.startlight.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {
    private String kk_nickname;

    private String profile_img;

    private String st_nickname;

    public static MemberDto toDto(Member member) {
        return MemberDto.builder()
                .kk_nickname(member.getKk_nickname())
                .profile_img(member.getProfile_img())
                .st_nickname(member.getSt_nickname())
                .build();
    }

}
