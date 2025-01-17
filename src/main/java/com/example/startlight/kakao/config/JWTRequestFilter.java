package com.example.startlight.kakao.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JWTRequestFilter.class);
    private final JWTUtils jwtUtils;

    public JWTRequestFilter(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.debug("Entering JWTRequestFilter");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            logger.debug("Cookies found: " + Arrays.toString(cookies));
            Cookie authCookie = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("AUTH-TOKEN"))
                    .findAny().orElse(null);
            if (authCookie != null) {
                logger.debug("Auth Cookie found: " + authCookie.getValue());
                Authentication authentication = jwtUtils.verifyAndGetAuthentication(authCookie.getValue());
                if (authentication != null) {
                    logger.debug("Authentication successful: " + authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    logger.debug("Authentication failed");
                }
            } else {
                logger.debug("Auth Cookie not found");
            }
        } else {
            logger.debug("No cookies found");
        }
        logger.debug("Exiting JWTRequestFilter");
        filterChain.doFilter(request, response);
    }
}
