package com.example.startlight.postComment.service;

import com.example.startlight.member.dao.MemberDao;
import com.example.startlight.member.entity.Member;
import com.example.startlight.post.dao.PostDao;
import com.example.startlight.postComment.dao.PostCommentDao;
import com.example.startlight.postComment.dto.PostCommentRepDto;
import com.example.startlight.postComment.dto.PostCommentReqDto;
import com.example.startlight.postComment.entity.PostComment;
import com.example.startlight.postComment.repository.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostCommentService {
    private final PostCommentDao postCommentDao;
    private final PostDao postDao;
    private final MemberDao memberDao;

    public List<PostCommentRepDto> createPostComment(PostCommentReqDto postCommentReqDto) {
        try {
            // TODO: 실제 사용자 ID를 가져와야 함
            Long userId = 3879188713L; // `UserUtil.getCurrentUserId();` 로 변경 가능
            Member member = memberDao.selectMember(userId);

            if (member == null) {
                throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
            }

            // 댓글 객체 생성
            PostComment postComment = PostComment.toEntity(postCommentReqDto, postDao, member);

            // 댓글 저장 및 해당 게시글의 전체 댓글 목록 가져오기
            List<PostComment> postCommentList = postCommentDao.createPostCommentAndGetAll(postCommentReqDto.getPostId(), postComment);

            // `PostCommentRepDto` 리스트로 변환 후 반환
            return postCommentList.stream()
                    .map(PostCommentRepDto::toDto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("댓글 작성 중 오류 발생", e);
        }
    }
}
