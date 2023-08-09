package com.likeurator.squadmania_auth.auth;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;


    @PutMapping("/withdraw/{email}")
    public void withdraw(@PathVariable(name = "email") String email){
        authorizationService.withdraw(email);
    }

}
