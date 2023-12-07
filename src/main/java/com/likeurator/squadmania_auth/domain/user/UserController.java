package com.likeurator.squadmania_auth.domain.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.likeurator.squadmania_auth.domain.user.model.Userinfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired private final UserService userService;
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Optional<Userinfo>> findUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.findByEmail(email));
    }
    
    @GetMapping("/lists")
    public ResponseEntity<List<Userinfo>> getUserAll(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    //차후 수정 예정
    @PutMapping("/{email_id}")
    public void updateInfo(@PathVariable(name = "email_id") String email){
        
    }

}
