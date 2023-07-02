package com.likeurator.squadmania_auth.domain.oauth2;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.likeurator.squadmania_auth.auth.model.RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController("/api/v1/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {
    CustomOAuth2UserService oauth2SUserService;

    @GetMapping("/login")
    public String login() {
        return "login"; // 로그인 페이지로 이동하는 뷰 이름
    }

    @GetMapping("/success")
    public String success() {
        return "success"; // 로그인 성공 후 이동할 뷰 이름
    }
}
