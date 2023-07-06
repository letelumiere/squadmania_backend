package com.likeurator.squadmania_auth.domain.oauth2;

import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.likeurator.squadmania_auth.domain.user.UserRepository;
import com.nimbusds.jose.shaded.gson.Gson;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;
    
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

    private String codeVerifier = "YOUR_CODE_VERIFIER";
    private String codeChallenge = "YOUR_CODE_CHALLENGE";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        throw new UnsupportedOperationException("Unimplemented method 'loadUser'");
    }

    public String kakaoCallback(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = requestHeaders(new HttpHeaders());
        MultiValueMap parameters = requestParameters(code, new LinkedMultiValueMap());
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://kauth.kakao.com/oauth/token", request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Gson gson = new Gson();
            Map<String, Object> responseMap = gson.fromJson(response.getBody(), Map.class);

            for(Object obj : responseMap.keySet()){
                log.info(" obj : {} ", obj + " " + responseMap.get(obj));
            }

            String accessToken = (String) responseMap.get("access_token");
            log.info("Access Token: {}", accessToken);
            return accessToken;
        } else {
            log.error("Error occurred while fetching access token: {}", response.getStatusCode());
            return "Error";
        }
    }

    public HttpHeaders requestHeaders(HttpHeaders headers){
        String credentials = kakaoClientId + ":" + kakaoClientSecret;
        String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));
        
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Basic " + encodedCredentials);

        return headers;
    }

    public MultiValueMap<String, String> requestParameters(String code, LinkedMultiValueMap linkedMultiValueMap){
        MultiValueMap<String,String> parameters = linkedMultiValueMap;

        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", kakaoClientId);
        parameters.add("client_secret", kakaoClientSecret); // Add client_secret
        parameters.add("redirect_uri", "http://localhost:8080/api/v1/oauth2/code/kakao");
        parameters.add("code", code);
        parameters.add("code_verifier", codeVerifier);

        return parameters;
    }
}
