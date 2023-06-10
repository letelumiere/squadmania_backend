package com.likeurator.squadmania_auth;

import java.security.Timestamp;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestPropertySource;

import com.likeurator.squadmania_auth.auth.AuthenticationController;
import com.likeurator.squadmania_auth.auth.AuthenticationService;
import com.likeurator.squadmania_auth.auth.model.AuthenticationRequest;
import com.likeurator.squadmania_auth.auth.model.AuthenticationResponse;
import com.likeurator.squadmania_auth.auth.model.RegisterRequest;
import com.likeurator.squadmania_auth.auth.model.RestRequest;
import com.likeurator.squadmania_auth.config.JwtService;
import com.likeurator.squadmania_auth.domain.user.Role;
import com.likeurator.squadmania_auth.domain.user.UserController;
import com.likeurator.squadmania_auth.domain.user.UserRepository;
import com.likeurator.squadmania_auth.domain.user.UserService;
import com.likeurator.squadmania_auth.domain.user.model.Userinfo;

import jakarta.persistence.Embeddable;

//@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED.NONE)
@SpringBootTest
public class AppTest {
    @Autowired AuthenticationService authService;
    @Autowired UserService userService;

    @Test
    void registerTest(){
        var request = RegisterRequest.builder()
            .email_id("sim@gmail.com")
            .password("1234")
            .build();

        
        var response = authService.register(request);
        var simyoung = userService.findByEmail(request.getEmail_id())
            .orElseThrow(null);
        
        System.out.println("==================Test Output=================");
        System.out.println(simyoung.getEmailId()+" "+simyoung.getPassword());
        System.out.println(simyoung.getAuthId()+" "+simyoung.getAuthType());
        System.out.println(simyoung.getUsername()+" ");
        System.out.println(simyoung.getCreatedAt()+" ");
        System.out.println("==================Test Output=================");
        
    }  

    void loginTest(){

    }

    void logoutTest(){

    }

    void reassuarenceTest(){

    }
    

    @Test
    void tokenTest(){

    }



}
