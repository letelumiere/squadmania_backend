package com.likeurator.squadmania_auth.domain.user;

import java.util.*;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.likeurator.squadmania_auth.auth.AuthenticationService;
import com.likeurator.squadmania_auth.auth.RestRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired private final UserService userService;
    @Autowired private final AuthenticationService authService;

    @PostMapping("/sign")
    public ResponseEntity<Userinfo> createUserinfo(@RequestBody Userinfo user){    //1. 아이디 create -회원가입 (parameter : auth_id)
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
            return ResponseEntity.created(uri).body(userService.signUpUser(user));    
    }   
    
//    @GetMapping("/email/{email}")
//    public ResponseEntity<Userinfo> getAccountByScreenName(@PathVariable String email){
//        return ResponseEntity.ok(userService.getUserByEmail(email));
//    }


    @GetMapping("/email/{email}")
    public ResponseEntity<Optional<Userinfo>> findUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("/lists")
    public ResponseEntity<List<Userinfo>> getUserAll(){
        return ResponseEntity.ok(userService.findAllUsers());
    }


    @PutMapping("/modify")
    public ResponseEntity<UserUpdateResponse> findAndUpdateUser(@RequestBody RestRequest request, @RequestParam String password){
        return ResponseEntity.ok(userService.findAndUpdateUser(request, password));
    }

    @DeleteMapping("/sign-out")
    public ResponseEntity<Userinfo> deleteAccount(@RequestBody UserUpdateRequest request){    //추후에 requestBody 넣을 것
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    
    private void reliveUserinfo(Long id, String email){
        
    }


}


//2.	Userinfo get - 로그인용 (parameter : auth_id)
//3.	Userinfo 수정 - 닉네임 변경 및 다른 정보들 변경 (parameter : id)
//4.	Userinfo 회원탈퇴 (param: id)
//5.	휴면아이디 접속했을때, 다른 저장소에서 개인정보를 가져와서 Userinfo 에 다시 넣기 (param: id, email_id)
//6.	관리자용 API 는 별도로 만들어야 함

