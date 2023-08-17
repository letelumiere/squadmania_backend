package com.likeurator.squadmania_auth.auth;


import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.likeurator.squadmania_auth.auth.model.AuthenticationRequest;
import com.likeurator.squadmania_auth.auth.model.AuthenticationResponse;
import com.likeurator.squadmania_auth.auth.model.RegisterRequest;
import com.likeurator.squadmania_auth.auth.model.RestRequest;
import com.likeurator.squadmania_auth.config.JwtService;
import com.likeurator.squadmania_auth.domain.user.Role;
import com.likeurator.squadmania_auth.domain.user.UserRepository;
import com.likeurator.squadmania_auth.domain.user.model.Userinfo;
import com.likeurator.squadmania_auth.token.AccessToken;
import com.likeurator.squadmania_auth.token.RefreshToken;
import com.likeurator.squadmania_auth.token.AccessTokenRepository;
import com.likeurator.squadmania_auth.token.RefreshTokenRepository;
import com.likeurator.squadmania_auth.token.TokenType;

import jakarta.transaction.Transactional;
import lombok.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AccessTokenRepository tokenRepository;
    private final RefreshTokenRepository refreshRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    
    private final AuthenticationManager authenticationManager;

    //DB에 등록. 토큰 생성
    public AuthenticationResponse register(RegisterRequest request)  {
        var user = Userinfo.builder()
            .emailId(request.getEmail_id())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .withdraw(false)
            .build();

        userRepository.save(user);
        var savedUser = userRepository.save(user);

        var accessToken = jwtService.generateAccessToken(user);
        saveUserAccessToken(savedUser, accessToken);

        var refreshToken = jwtService.generateRefreshToken(user);        
        saveUserRefreshToken(savedUser, refreshToken);

        return AuthenticationResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build(); 
    }
    
    //새로운 accessToken과 refreshToken 생성
    //Token이 expired됐을 시, client에서는 header의 authorization을 null하고 authenticate로 보낸다.
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail_id(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail_id())
            .orElseThrow(null);

        if(user.getWithdraw()) {
            user.setWithdraw(false); 
            user.setWithdrawDate(null);
        }
        
        revokeAllUserTokens(user);

        var accessToken = jwtService.generateAccessToken(user);
        saveUserAccessToken(user, accessToken);

        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserRefreshToken(user, refreshToken);
        
        return AuthenticationResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
    
    public AuthenticationResponse reAuthenticate(String email, AuthenticationRequest request){
        var user = userRepository.findByEmail(email)
            .orElseThrow(() -> new NullPointerException("해당 responseBody가 무존재"));

        user.setEmailId(request.getEmail_id());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return authenticate(request);
    }

    //case1 : access token과 refresh token 모두가 만료된 경우 →  발생 (재 로그인하여 둘다 새로 발급)
    //case2 : access token은 만료됐지만, refresh token은 유효한 경우 →  refresh token을 검증하여 access token 재발급
    //case3 : access token은 유효하지만, refresh token은 만료된 경우 →  access token을 검증하여 refresh token 재발급                    
    //case4 : access token과 refresh token 모두가 유효한 경우 → 정상 처리
    public AuthenticationResponse reIssuance(RestRequest request, String jwtAccessToken) {
        var user = userRepository.findByEmail(request.getEmail_id())
            .orElseThrow(null);
        // 해당 로직까지는 받아지는 것 같으나, 여기에서 진전이 없음

        System.out.println(user.getEmailId());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail_id());

        var jwtRefreshToken = refreshRepository.findRefreshTokenByUsername(request.getEmail_id())
            .orElseThrow(null);


            
        String accessToken = jwtAccessToken.substring(7);
        String refreshToken = jwtRefreshToken.getToken();
        
        // 토큰 삭제 후 재발급 로직에서 뭔가 꼬인 것으로 보임
        // sql에 저장되는 token과 redis에 저장되는 token의 과정 알고리즘이 차이가 있음
        // access, refresh token의 재발급 조건에서 꼬인듯?  
        // 
        if(!jwtService.isTokenValid(accessToken, userDetails) && !jwtService.isTokenValid(refreshToken, userDetails)){
            revokeAllUserTokens(user);
        }else{
            if(jwtService.isTokenIssuer(accessToken, userDetails)){
                var token = tokenRepository.findByToken(accessToken)
                        .orElseThrow(null);
                token.setExpired(true);
                token.setRevoked(true);
                tokenRepository.save(token);
    
                accessToken = jwtService.generateAccessToken(userDetails);            
                saveUserAccessToken(user, accessToken);
            }
    
            if(jwtService.isTokenIssuer(refreshToken, userDetails)){
                var token = refreshRepository.findByToken(refreshToken)
                    .orElseThrow(null);

                refreshRepository.save(token);
    
                refreshToken = jwtService.generateRefreshToken(userDetails);
                saveUserRefreshToken(user, refreshToken);
            }    
        }

        System.out.println("hhhhhhhhhhhhhhhhaㅏㅏㅏㅏㅏㅏㅏㅏㅏ");

        return AuthenticationResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    private void saveUserAccessToken(Userinfo user, String jwtToken) {
        var accessToken = AccessToken.builder()
            .id(user.getId().toString())
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();

        tokenRepository.save(accessToken);
    }

    private void saveUserRefreshToken(Userinfo user, String jwtToken) {
        var refreshToken = RefreshToken.builder()
            .id(user.getId().toString())
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();

        refreshRepository.save(refreshToken);
    }    

    // https://velog.io/@backtony/Redis-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EC%9E%85%EC%B6%9C%EB%A0%A5-%EB%B0%8F-%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0-%EC%8B%A4%EC%8A%B5%ED%95%98%EA%B8%B0
    private void revokeAllUserTokens(Userinfo user) {
        var validUserTokens = tokenRepository.findById(user.getId().toString())
            .orElseThrow(null);
        var validRefreshTokens = refreshRepository.findById(user.getId().toString())
            .orElseThrow(null);

        tokenRepository.delete(validUserTokens);
        refreshRepository.delete(validRefreshTokens);
    }
    
    //1.회원가입이 되어있는지 확인 뒤
        //2.ROLE에 탈퇴예정을 update하고
            //3.탈퇴예정일 역시 업데이트를 한다. (한달 뒤)
            //user.setWithdrawDate(); 현재시간+30일 뒤. 년월일만 체크
    
    //sql에서는 해당 일시가 되어있는 column을 삭제한다. 혹은 uuid와 ROLE만 남기고 나머지 행만 삭제한다. 
    public void withdraw(String email){        
        var user = userRepository.findByEmail(email)
            .orElseThrow(null);

        user.setWithdraw(true);
        user.setWithdrawDate(new Date(System.currentTimeMillis() + 100 * 60 * 24L));   //임시 시간   
        userRepository.save(user);
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 10)   //시간 예약 수정 필요
    @Transactional
    public void withdrawMembers(){
        Date date = new Date(System.currentTimeMillis());
        List<Userinfo> memberList = userRepository.isWithdraws(date);

        for(Userinfo user : memberList){
            userRepository.delete(user);
        }
    } 
}