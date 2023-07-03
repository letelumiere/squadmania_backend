package com.likeurator.squadmania_auth.domain.oauth2;

import java.util.Base64;
import java.util.Map;

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
@RestController
@RequestMapping("api/v1/oauth2")
@Slf4j
public class OAuth2Controller {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;
    
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

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