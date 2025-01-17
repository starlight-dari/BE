package com.example.startlight.kakao;

import com.example.startlight.kakao.dto.KakaoTokenResponseDto;
import com.example.startlight.kakao.dto.KakaoUserInfoResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class KakaoService {

    private final String clientId;
    private final String redirectUri;
    private final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
    private final String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";

    private final String KAKAO_TOKEN_URI = "https://kauth.kakao.com/oauth/token";

    private final String KAKAO_USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    @Autowired
    public KakaoService(
            @Value("${kakao.client.id}") String clientId,
            @Value("${kakao.redirect.uri}") String uri
    ) {
        this.clientId = clientId;
        this.redirectUri = uri;
    }

    public String getAccessTokenFromKakao(String code) {

        log.info("Request Params: grant_type=authorization_code, client_id={}, redirect_uri={}, code={}", clientId, redirectUri, code);

        // 동기적으로 WebClient 요청 수행
        String response = WebClient.create(KAUTH_TOKEN_URL_HOST)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth/token")
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", clientId)
                        .with("redirect_uri", redirectUri)
                        .with("code", code))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    // 상태 코드와 응답 Body를 로그로 출력
                    return clientResponse.bodyToMono(String.class).flatMap(body -> {
                        log.error("Error Response: Status={}, Body={}", clientResponse.statusCode(), body);
                        return Mono.error(new RuntimeException("Invalid Parameter: " + body));
                    });
                })
                .bodyToMono(String.class)
                .block(); // 동기적으로 응답 대기

        log.info("Kakao API Response: {}", response);

        // JSON -> DTO 변환 및 accessToken 추출
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 추가 필드 무시
            KakaoTokenResponseDto dto = objectMapper.readValue(response, KakaoTokenResponseDto.class);
            log.info("Access Token: {}", dto.getAccessToken());
            return dto.getAccessToken(); // accessToken 반환
        } catch (JsonProcessingException e) {
            log.error("JSON Parsing Error: {}", e.getMessage());
            throw new RuntimeException("Failed to parse response: " + response, e);
        }
    }

    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {

        KakaoUserInfoResponseDto userInfo = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // access token 인가
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                //TODO : Custom Exception
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .block();

        log.info("[ Kakao Service ] Auth ID ---> {} ", userInfo.getId());
        log.info("[ Kakao Service ] NickName ---> {} ", userInfo.getKakaoAccount().getProfile().getNickName());
        log.info("[ Kakao Service ] ProfileImageUrl ---> {} ", userInfo.getKakaoAccount().getProfile().getProfileImageUrl());

        return userInfo;
    }

//    public Mono<KakaoTokenResponseDto> getAccessTokenFromKakao(String code) {
//
//        log.info("Request Params: grant_type=authorization_code, client_id={}, redirect_uri={}, code={}", clientId, redirectUri, code);
//
//        Mono<KakaoTokenResponseDto> resFromKakao = WebClient.create(KAUTH_TOKEN_URL_HOST)
//                .post()
//                .uri(uriBuilder -> uriBuilder
//                        .scheme("https")
//                        .path("/oauth/token")
//                        .build())
//                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
//                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
//                        .with("client_id", clientId)
//                        .with("redirect_uri", redirectUri)
//                        .with("code", code))
//                .retrieve()
//                .onStatus(HttpStatusCode::isError, clientResponse -> {
//                    // 상태 코드와 응답 Body를 로그로 출력
//                    return clientResponse.bodyToMono(String.class).flatMap(body -> {
//                        log.error("Error Response: Status={}, Body={}", clientResponse.statusCode(), body);
//                        return Mono.error(new RuntimeException("Invalid Parameter: " + body));
//                    });
//                })
//                .bodyToMono(String.class) // 응답을 String으로 수신
//                .flatMap(response -> {
//                    log.info("Kakao API Response: {}", response);
//                    try {
//                        // JSON -> DTO 변환
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 추가 필드 무시
//                        KakaoTokenResponseDto dto = objectMapper.readValue(response, KakaoTokenResponseDto.class);
//                        return Mono.just(dto);
//                    } catch (JsonProcessingException e) {
//                        log.error("JSON Parsing Error: {}", e.getMessage());
//                        return Mono.error(new RuntimeException("Failed to parse response: " + response, e));
//                    }
//                })
//                .cache();
//
//        return resFromKakao;
//    }



//        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
//                .uri(uriBuilder -> uriBuilder
//                        .scheme("https")
//                        .path("/oauth/token")
//                        .build())
//                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8") // 헤더 설정
//                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
//                        .with("client_id", clientId)
//                        .with("redirect_uri", redirectUri)
//                        .with("code", code)) // 폼 데이터 전송
//                .retrieve()
//                //TODO : Custom Exception
//                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
//                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
//                .bodyToMono(KakaoTokenResponseDto.class)
//                .block();
//
//
//        log.info(" [Kakao Service] Access Token ------> {}", kakaoTokenResponseDto.getAccessToken());
//        log.info(" [Kakao Service] Refresh Token ------> {}", kakaoTokenResponseDto.getRefreshToken());
//        //제공 조건: OpenID Connect가 활성화 된 앱의 토큰 발급 요청인 경우 또는 scope에 openid를 포함한 추가 항목 동의 받기 요청을 거친 토큰 발급 요청인 경우
//        log.info(" [Kakao Service] Id Token ------> {}", kakaoTokenResponseDto.getIdToken());
//        log.info(" [Kakao Service] Scope ------> {}", kakaoTokenResponseDto.getScope());
//
//        return kakaoTokenResponseDto.getAccessToken();


//    @Transactional
//    public KakaoTokenDto getKakaoAccessToken(String code) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // Http Response Body 객체 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code"); //카카오 공식문서 기준 authorization_code 로 고정
//        params.add("client_id", clientId); // 카카오 Dev 앱 REST API 키
//        params.add("redirect_uri", redirectUri); // 카카오 Dev redirect uri
//        params.add("code", code); // 프론트에서 인가 코드 요청시 받은 인가 코드값
//
//        // 헤더와 바디 합치기 위해 Http Entity 객체 생성
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
//
//        // 카카오로부터 Access token 받아오기
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> accessTokenResponse = rt.exchange(
//                KAKAO_TOKEN_URI, // "https://kauth.kakao.com/oauth/token"
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//        // JSON Parsing (-> KakaoTokenDto)
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        KakaoTokenDto kakaoTokenDto = null;
//        try {
//            kakaoTokenDto = objectMapper.readValue(accessTokenResponse.getBody(), KakaoTokenDto.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return kakaoTokenDto;
//    }

//    public ResponseEntity<LoginResponseDto> kakaoLogin(String kakaoAccessToken) {
//        Account account = getKakaoInfo(kakaoAccessToken);
//
//        LoginResponseDto loginResponseDto = new LoginResponseDto();
//        loginResponseDto.setLoginSuccess(true);
//        loginResponseDto.setAccount(account);
//
//        Account existOwner = accountRepository.findById(account.getId()).orElse(null);
//        try {
//            if (existOwner == null) {
//                System.out.println("처음 로그인 하는 회원입니다.");
//                accountRepository.save(account);
//            }
//            loginResponseDto.setLoginSuccess(true);
//
//            return ResponseEntity.ok().headers(headers).body(loginResponseDto);
//
//        } catch (Exception e) {
//            loginResponseDto.setLoginSuccess(false);
//            return ResponseEntity.badRequest().body(loginResponseDto);
//        }
//    }
//
//    public Account getKakaoInfo(String kakaoAccessToken) {
//        RestTemplate rt = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + kakaoAccessToken);
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        HttpEntity<MultiValueMap<String, String>> accountInfoRequest = new HttpEntity<>(headers);
//
//        // POST 방식으로 API 서버에 요청 후 response 받아옴
//        ResponseEntity<String> accountInfoResponse = rt.exchange(
//                KAKAO_USER_INFO_URI, // "https://kapi.kakao.com/v2/user/me"
//                HttpMethod.POST,
//                accountInfoRequest,
//                String.class
//        );
//
//        // JSON Parsing (-> kakaoAccountDto)
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        KakaoAccountDto kakaoAccountDto = null;
//        try {
//            kakaoAccountDto = objectMapper.readValue(accountInfoResponse.getBody(), KakaoAccountDto.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        // 회원가입 처리하기
//        Long kakaoId = kakaoAccountDto.getId();
//        Account existOwner = accountRepository.findById(kakaoId).orElse(null);
//        // 처음 로그인이 아닌 경우
//        if (existOwner != null) {
//            return Account.builder()
//                    .id(kakaoAccountDto.getId())
//                    .email(kakaoAccountDto.getKakao_account().getEmail())
//                    .kakaoName(kakaoAccountDto.getKakao_account().getProfile().getNickname())
//                    .build();
//        }
//        // 처음 로그인 하는 경우
//        else {
//            return Account.builder()
//                    .id(kakaoAccountDto.getId())
//                    .email(kakaoAccountDto.getKakao_account().getEmail())
//                    .kakaoName(kakaoAccountDto.getKakao_account().getProfile().getNickname())
//                    .build();
//        }
//    }
}
