package com.likeurator.squadmania_auth;

import java.security.Timestamp;
import java.util.HashMap;
import java.util.List;

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
import com.likeurator.squadmania_auth.token.AccessToken;
import com.likeurator.squadmania_auth.token.RefreshTokenRepository;
import com.likeurator.squadmania_auth.token.TokenRepository;

import io.jsonwebtoken.Claims;
import jakarta.persistence.Embeddable;

//@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED.NONE)
@SpringBootTest
public class AppTest {
    static HashMap<String, RegisterRequest> hMap = new HashMap<>();
    @Autowired UserRepository userRepository;
    @Autowired TokenRepository tokenRepository;
    @Autowired RefreshTokenRepository refreshTokenRepository;
    @Autowired AuthenticationService authService;
    @Autowired UserService userService;
    @Autowired JwtService jwtService;


    //given
    //when
    //then
    static void create(){
        var user01 = RegisterRequest.builder()
            .email_id("simyoung@gmail.com")
            .password("1234")
            .build();
        hMap.put("user01", user01);

        var user02 = RegisterRequest.builder()
            .email_id("doctor@gmail.com")
            .password("1234")
            .build();
        hMap.put("user02", user02);

        var user03 = RegisterRequest.builder()
            .email_id("shanghai@gmail.com")
            .password("1234")
            .build();
        hMap.put("user03", user03);
    }

    
    void registerTest(){
        create();

        for(String s : hMap.keySet()){
            var request = hMap.get(s);
            
            var token = authService.register(request);
            var object = userService.findByEmail(request.getEmail_id())
                .orElseThrow(null);

        System.out.println("==================Test Output=================");
        System.out.println(object.getEmailId()+" "+object.getPassword());
        System.out.println(object.getAuthId()+" "+object.getAuthType());
        System.out.println(object.getUsername()+" "+object.getCreatedAt());
        System.out.println("==================Test Output=================");
        System.out.println(token.getAccessToken());
        System.out.println(token.getRefreshToken());                
        System.out.println("==================Test Output=================");

        }
    } 

    @Test
    void tokenTest(){
        registerTest();

        var email = hMap.get("user01").getEmail_id();
        var user01 = userService.findByEmail(email)
            .orElseThrow(null);
        var accessToken = tokenRepository.findAllValidTokenByUser(user01.getId());
        
        for(var n : accessToken){
            var date = jwtService.extractAllClaims(n.getToken());

            System.out.println(n.isExpired()+" "+n.isRevoked());
            System.out.println("expiration =" + date.getExpiration());
        }   
    }
    void reassuarnceTest(){
        registerTest();
    }
    void filterTest(){
        registerTest();
    }
    void loginTest(){

    }
    void logoutTest(){

    }
}
