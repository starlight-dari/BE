package com.example.startlight.kakao;

import com.example.startlight.kakao.config.JWTUtils;
import com.example.startlight.kakao.dto.KakaoUserInfoResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/auth/kakao")
public class KakaoOauthController {
    private final KakaoService kakaoService;
    private final JWTUtils jwtTokenProvider;

    public KakaoOauthController(KakaoService kakaoService, JWTUtils jwtTokenProvider) {
        this.kakaoService = kakaoService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/callback")
    public ResponseEntity<?> kakaoLogin(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            return ResponseEntity.badRequest().body("Authorization code is missing");
        }

        try {
            // 1. Access Token 가져오기
            String accessToken = kakaoService.getAccessTokenFromKakao(code);

            // 2. 사용자 정보 가져오기
            KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
            log.info("Kakao User Info: {}", userInfo);

            // 3. JWT 생성
            boolean rememberMe = false; // 예: 클라이언트 요청에 따라 결정
            String jwtToken = jwtTokenProvider.createToken(userInfo, rememberMe);

            // 5. 인증 객체 확인 (로그 추가)
            Authentication authentication = jwtTokenProvider.verifyAndGetAuthentication(jwtToken);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            final ResponseCookie cookie = ResponseCookie.from("AUTH-TOKEN", jwtToken)
                    .httpOnly(true)
                    .maxAge(7 * 24 * 3600)
                    .path("/")
                    .secure(false)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            log.info(authentication.getPrincipal().toString());

            // 6. 응답 반환
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful!",
                    "user", userInfo,
                    "token", jwtToken
            ));

        } catch (Exception e) {
            log.error("Error during Kakao login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Login failed: " + e.getMessage());
        }
    }
}
