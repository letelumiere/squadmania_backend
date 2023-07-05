package com.likeurator.squadmania_auth.domain.oauth2;

import java.util.Base64;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.likeurator.squadmania_auth.auth.model.RegisterRequest;
import com.nimbusds.jose.shaded.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//참고 url
//https://accounts.kakao.com/login/?continue=https%3A%2F%2Fkauth.kakao.com%2Foauth%2Fauthorize%3Fscope%3Daccount_email%2520profile_nickname%2520profile_image%2520openid%26response_type%3Dcode%26state%3Dj-Fw5WxVA-pSMDTjhVWmDnrb5OCFOM9uPVlNOfT0B_w%253D%26redirect_uri%3Dhttp%253A%252F%252Flocalhost%253A8080%252Fapi%252Fv1%252Foauth2%252Fcode%252Fkakao%26through_account%3Dtrue%26nonce%3D1LFGBco_otbu0emfcFyBjxoN8b4joityN7WxKyaKoA8%26client_id%3Df66ad78db368781970e4086debb56661#login
//http://localhost:8080/api/v1/oauth2/code/kakao?code=rNVIDULG56fOl_O3CiUmrk74XkStgdAueG8mFFusNGwKizXQzXgM97PK7gM3RSQodJ-CtwopyNoAAAGJJO5JtQ&state=j-Fw5WxVA-pSMDTjhVWmDnrb5OCFOM9uPVlNOfT0B_w%3D

@RestController
@RequestMapping("api/v1/oauth2")
@Slf4j
public class OAuth2Controller {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;
    
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

    @Autowired
    private CustomOAuth2UserService oAuth2UserService;

    //로직
    //소셜 로그인 - 회원가입 - 이메일 인증 - 로그인     
    //1. 카카오에 로그인 -> securitychainfilter로 해결
        //세션이 구현되어야 함.
    //2. 카카오에서 authorization code 받아서 회원가입
    //3. 카카오에 로그인 -> 카카오 아이디로 로그인
    //간편가입 redirectUrl 등록을 안함 ㅋ 해야 하나?
        //accessToken이 부여됨.
    //회원 정보에서 provider를 구분해야 하나?


    @ResponseBody
    @GetMapping("/code/kakao")
    public String kakaoCallback(@RequestParam String code) {

        RestTemplate restTemplate = new RestTemplate();

        String codeVerifier = "YOUR_CODE_VERIFIER";
        String codeChallenge = "YOUR_CODE_CHALLENGE";

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", kakaoClientId);
        parameters.add("client_secret", kakaoClientSecret); // Add client_secret
        parameters.add("redirect_uri", "http://localhost:8080/api/v1/oauth2/code/kakao");
        parameters.add("code", code);
        parameters.add("code_verifier", codeVerifier);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);
        String credentials = kakaoClientId + ":" + kakaoClientSecret;
        String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));
        headers.add("Authorization", "Basic " + encodedCredentials);

        ResponseEntity<String> response = restTemplate.postForEntity("https://kauth.kakao.com/oauth/token", request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Gson gson = new Gson();

            Map<String, Object> responseMap = gson.fromJson(response.getBody(), Map.class);
            String accessToken = (String) responseMap.get("access_token");
            log.info("Access Token: {}", accessToken);
            return accessToken;
        } else {
            log.error("Error occurred while fetching access token: {}", response.getStatusCode());
            return "Error";
        }
    }
}