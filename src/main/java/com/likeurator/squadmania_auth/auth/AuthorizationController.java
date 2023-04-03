package com.likeurator.squadmania_auth.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class AuthorizationController {
    private AuthorizationService authorizationService;


    @PutMapping
    public void withdraw(String email){
        
    }
}
