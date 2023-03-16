package com.likeurator.squadmania_auth.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.likeurator.squadmania_auth.domain.user.Userinfo;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")   //말 그대로 회원 가입  
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")   //로그인 시 Token 체크. //로그인 때 마다 accessToken 발급.
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    //https://junhyunny.github.io/spring-boot/spring-security/issue-and-reissue-json-web-token/ <- 참조할것
    //jwtExpiration이 됐을 때가 문제 <- client에서 될까?
    @PostMapping("/reIssuance")
    public ResponseEntity<AuthenticationResponse> reIssuance(@RequestBody RestRequest request, @RequestHeader("Authorization") String jwtAccessToken) {
        //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW0yMzQ1QGVtYWlsLmNvbSIsImlhdCI6MTY3ODk1MzA2OCwiZXhwIjoxNjc4OTUzMDk3fQ.pIh3ZbKswpJPaHlZfLgFRQSBAp4HNns0bkGj1_ovzs0
        System.out.println("token = " + jwtAccessToken);

        return ResponseEntity.ok(service.reIssuance(request, jwtAccessToken));
    }
}
