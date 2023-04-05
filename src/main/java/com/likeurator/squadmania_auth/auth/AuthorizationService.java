package com.likeurator.squadmania_auth.auth;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.likeurator.squadmania_auth.domain.user.UserRepository;
import com.likeurator.squadmania_auth.token.RefreshTokenRepository;
import com.likeurator.squadmania_auth.token.TokenRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthorizationService {    
    UserRepository userRepository;
    TokenRepository tokenRepository;
    RefreshTokenRepository refreshRepository;


    //1.회원가입이 되어있는지 확인 뒤
        //2.ROLE에 탈퇴예정을 update하고
            //3.탈퇴예정일 역시 업데이트를 한다. (한달 뒤)
            //user.setWithdrawDate(); 현재시간+30일 뒤. 년월일만 체크
    
    //sql에서는 해당 일시가 되어있는 column을 삭제한다. 혹은 uuid와 ROLE만 남기고 나머지 행만 삭제한다. 
    
    
    public void withdraw(String email){
        var user = userRepository.findByEmail("email")
        .orElseThrow(null);
    
        user.setWithdraw(true);
        user.setWithdrawDate(new Date(System.currentTimeMillis() + (30 * 24 * 60 * 60 * 1000L)));
        
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    @Transactional
    public void withdraw(){


    } 

    
}

//LocalDateTime time = LocalDateTime.now().plusDays(30);
