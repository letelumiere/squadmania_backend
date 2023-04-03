package com.likeurator.squadmania_auth.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PutMapping("/re-authenticate/{email}") //아이디 혹은 패스워드 바꿀 때. 
    public ResponseEntity<AuthenticationResponse> reAuthenticate(@PathVariable(name = "email") String email, @RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.reAuthenticate(email, request));
    }

    //https://junhyunny.github.io/spring-boot/spring-security/issue-and-reissue-json-web-token/ <- 참조할것
    //jwtExpiration이 됐을 때가 문제 <- client에서 될까?
    @PostMapping("/reIssuance")
    public ResponseEntity<AuthenticationResponse> reIssuance(@RequestBody RestRequest request, @RequestHeader("Authorization") String jwtAccessToken) {
        return ResponseEntity.ok(service.reIssuance(request, jwtAccessToken));
    }

    @DeleteMapping("/sign-out")
    public ResponseEntity<Userinfo> deleteAccount(@PathVariable(name ="email") String email){    //추후에 requestBody 넣을 것
        return null;//ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
        
    private void withdraws(Long id, String email){
        
    }

}
