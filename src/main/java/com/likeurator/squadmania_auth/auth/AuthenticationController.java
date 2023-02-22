package com.likeurator.squadmania_auth.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.likeurator.squadmania_auth.domain.user.Userinfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));

    }
    // 아래 링크를 참조할 것
    // https://github.com/letelumiere/squadmania/blob/main/src/main/java/com/likeurator/squadmania/domain/UserController.java
    @GetMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.refresh(request));
    }

    @GetMapping("/logout")
    public ResponseEntity<AuthenticationResponse> logout(){
        return null;
    }
    private void reliveUserinfo(Long id, String email){
        
    }
    
//    public ResponseEntity<Userinfo> deleteAccount(@PathVariable long id){    //추후에 requestBody 넣을 것
//        Userinfo user = userService.getUserReferencedById(id);
//        userService.signOutUser(user);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }



}
