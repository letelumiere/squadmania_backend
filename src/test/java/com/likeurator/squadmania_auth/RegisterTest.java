package com.likeurator.squadmania_auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.likeurator.squadmania_auth.auth.AuthenticationController;
import com.likeurator.squadmania_auth.auth.AuthenticationService;
import com.likeurator.squadmania_auth.auth.model.AuthenticationResponse;
import com.likeurator.squadmania_auth.auth.model.RegisterRequest;
import com.likeurator.squadmania_auth.domain.user.UserRepository;
import com.likeurator.squadmania_auth.domain.user.UserService;
import com.likeurator.squadmania_auth.domain.user.model.Userinfo;

@SpringBootTest
public class RegisterTest {

    @Test
    @DisplayName("register")
    public void test1(){

    }
}
