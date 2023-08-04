package com.likeurator.squadmania_auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.likeurator.squadmania_auth.config.JwtService;
import com.likeurator.squadmania_auth.token.RedisToken;
import com.likeurator.squadmania_auth.token.RedisTokenRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTokenRepository repository;
    
    //1차 테스트 - redis가 작동하는지 여부만 확인
    @Test
    void test() {
        var token = RedisToken.builder()
            .token("test_token")
            .build();

        repository.save(token);

        var output = repository.findById(token.getId())
            .orElseThrow(null);

        log.info("output", output.getId() + " " + output.getToken());

        repository.count();
        repository.delete(token);
    }
}

/*
 https://developia.tistory.com/36 
 
 */