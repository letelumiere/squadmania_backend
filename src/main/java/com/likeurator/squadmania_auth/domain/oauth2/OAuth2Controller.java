package com.likeurator.squadmania_auth.domain.oauth2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.likeurator.squadmania_auth.domain.oauth2.model.OAuth2UserInfoResponse;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("api/v1/oauth2")
@Slf4j
public class OAuth2Controller {
    @Autowired
    private CustomOAuth2UserService oAuth2UserService;

    @GetMapping("/code/kakao")
    public ResponseEntity<OAuth2UserInfoResponse> kakaoCallback(@RequestParam String code) {
        log.info("code {} =", code);
        return ResponseEntity.ok(oAuth2UserService.kakaoCallback(code));
    }
}