package com.likeurator.squadmania_auth.auth;

import java.net.http.HttpClient.Redirect;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.likeurator.squadmania_auth.config.LogoutService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;
    private final LogoutService logoutService;


    @PutMapping("/withdraw/{email}")
    public void withdraw(@PathVariable(name = "email") String email){
        authorizationService.withdraw(email);
    }

}
