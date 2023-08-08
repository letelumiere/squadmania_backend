package com.likeurator.squadmania_auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.likeurator.squadmania_auth.config.JwtService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTokenRepository repository;
    
    //1차 테스트 - redis가 작동하는지 여부만 확인
    @Test
    void test() {

    /*
        https://developia.tistory.com/36 
    */

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

    // 해당 redis token 테스트가 비즈니스 로직에 작용하는지 확인
    // 필요 - 생성된 계정
    
    @Test
    void test2(){

    }
}
