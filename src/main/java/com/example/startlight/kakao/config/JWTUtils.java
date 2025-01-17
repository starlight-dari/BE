package com.example.startlight.kakao.config;

import com.example.startlight.kakao.dto.KakaoUserInfoResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//token provider
@Component
public class JWTUtils {
    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);
    private static final long TOKEN_VALIDITY = 86400000L;
    private static final long TOKEN_VALIDITY_REMEMBER = 2592000000L;
    private final Key key;

    public JWTUtils(@Value("${app.jwtSecret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(KakaoUserInfoResponseDto userInfoResponseDto, boolean rememberMe) {
        long now = (new Date()).getTime();
        Date validity = rememberMe ? new Date(now + TOKEN_VALIDITY_REMEMBER) : new Date(now + TOKEN_VALIDITY);

        return Jwts.builder()
                .setSubject(String.valueOf(userInfoResponseDto.getId())) // id를 Subject로 설정
                .claim("id", userInfoResponseDto.getId()) // id 클레임 추가
                .claim("nickname", userInfoResponseDto.getKakaoAccount().profile.nickName) // 추가 정보 예시
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication verifyAndGetAuthentication(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // JWT에서 id 클레임 추출
            Long id = claims.get("id",Long.class);
            String nickname = claims.get("nickname", String.class);

            // Principal로 사용자 정보를 Map 형태로 설정
            Map<String, Object> principal = new HashMap<>();
            principal.put("id", id);
            principal.put("nickname", nickname);

            // 권한 정보 가져오기
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get("role", String.class));

            // Authentication 객체 생성 시 id 포함
            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("JWT token verification failed", e);
            return null;
        }
    }

    public String extractEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("email", String.class);
    }
}
