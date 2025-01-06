package com.example.startlight.member.controller;

import com.example.startlight.member.dto.MemberDto;
import com.example.startlight.member.dto.MemberRequestDto;
import com.example.startlight.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/create")
    public ResponseEntity<MemberDto> createUser(
            @RequestBody MemberDto memberDto
    ) {
        MemberDto responseMemberDto = memberService.createMember(memberDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseMemberDto);
    }

    @GetMapping("/select")
    public ResponseEntity<MemberDto> selectUser(
            @RequestParam Long id
    ) {
        MemberDto memberDto = memberService.selectMember(id);
        return ResponseEntity.status(HttpStatus.OK).body(memberDto);
    }

    @PutMapping("/updateName")
    public ResponseEntity<MemberDto> updateUserName(
            @RequestBody MemberRequestDto memberRequestDto
            ) {
        MemberDto memberDto = memberService.updateMemberName(memberRequestDto.getId(), memberRequestDto.getNickname());
        return ResponseEntity.status(HttpStatus.OK).body(memberDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(
            @RequestBody Long id
    ) {
        memberService.deleteMember(id);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
}
