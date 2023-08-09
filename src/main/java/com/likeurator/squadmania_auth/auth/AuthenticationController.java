package com.likeurator.squadmania_auth.auth;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.likeurator.squadmania_auth.auth.model.AuthenticationRequest;
import com.likeurator.squadmania_auth.auth.model.AuthenticationResponse;
import com.likeurator.squadmania_auth.auth.model.RegisterRequest;
import com.likeurator.squadmania_auth.auth.model.RestRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final AuthorizationService authorizationService;

    
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
    @PostMapping("/re-issuance")
    public ResponseEntity<AuthenticationResponse> reIssuance(@RequestBody RestRequest request, @RequestHeader("Authorization") String jwtAccessToken) {
        return ResponseEntity.ok(service.reIssuance(request, jwtAccessToken));
    }

    @PutMapping("/withdraw/{email}")
    public ResponseEntity<?> withdraw(@PathVariable(name = "email") String email){
        authorizationService.withdraw(email);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/v1/auth/logout"));

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
