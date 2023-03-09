package com.likeurator.squadmania_auth.auth;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.likeurator.squadmania_auth.config.JwtService;
import com.likeurator.squadmania_auth.domain.user.Role;
import com.likeurator.squadmania_auth.domain.user.UserRepository;

import com.likeurator.squadmania_auth.domain.user.Userinfo;
import com.likeurator.squadmania_auth.token.AccessToken;
import com.likeurator.squadmania_auth.token.RefreshToken;
import com.likeurator.squadmania_auth.token.TokenRepository;
import com.likeurator.squadmania_auth.token.RefreshTokenRepository;
import com.likeurator.squadmania_auth.token.TokenType;

import lombok.RequiredArgsConstructor;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RefreshTokenRepository refreshRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    
    private final AuthenticationManager authenticationManager;


    //DB에 등록. 토큰 생성
    public AuthenticationResponse register(RegisterRequest request) {
        var user = Userinfo.builder()
            .emailId(request.getEmail_id())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
        userRepository.save(user);

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken, refreshToken);

        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build(); 
    }
    
    //새로운 accessToken과 refreshToken 생성
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail_id(),
                request.getPassword()
            )
        );
        var user = userRepository.findByEmail(request.getEmail_id())
            .orElseThrow(null);
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, token, refreshToken);
    
        return AuthenticationResponse.builder().accessToken(token).refreshToken(refreshToken).build();
    }

    //case1 : access token과 refresh token 모두가 만료된 경우 → 에러 발생 (재 로그인하여 둘다 새로 발급)
    //case2 : access token은 만료됐지만, refresh token은 유효한 경우 →  refresh token을 검증하여 access token 재발급
    //case3 : access token은 유효하지만, refresh token은 만료된 경우 →  access token을 검증하여 refresh token 재발급                    
    //case4 : access token과 refresh token 모두가 유효한 경우 → 정상 처리
    //**보완해야 할 것 = 해당 token의 expired 여부. //내일 테스트하자
    //https://junhyunny.github.io/spring-boot/spring-security/issue-and-reissue-json-web-token/ <- 참조할것
    public AuthenticationResponse refresh(RefreshRequest request) {
        String userName = jwtService.extractUsername(request.getAccessToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        var accessToken = "";
        var refreshToken = "";

        var user = userRepository.findByEmail(userName)
            .orElseThrow(null);

        if(!jwtService.isTokenValid(request.getAccessToken(), userDetails)){
            var token = tokenRepository.findByToken(accessToken)
                .orElseThrow(null);

            token.setExpired(true);
            token.setRevoked(true);
            accessToken = jwtService.generateToken(userDetails);
            
            tokenRepository.save(token);
        }
        
        if(!jwtService.isTokenValid(request.getRefreshToken(), userDetails)){
            var token = refreshRepository.findByToken(refreshToken)
                .orElseThrow(null);

            token.setExpired(true);
            refreshToken = jwtService.generateRefreshToken(userDetails);

            refreshRepository.save(token);
        }

        saveUserToken(user, accessToken, refreshToken);

        if(accessToken.equals("")) accessToken = request.getAccessToken();
        if(refreshToken.equals("")) refreshToken = request.getRefreshToken();

        return AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    //토큰 저장 메서드 -> accessToken은 localStorage 혹은 redis 저장될 예정. 지금은 sql로.
    //아마 OAuth2.0 적용하면서 한번 더 갈아야 할지도. 
    private void saveUserToken(Userinfo user, String jwtToken, String jwtRefreshToken) {
        if(jwtToken.equals("")){
            var accessToken = AccessToken.builder()
                .userinfo(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
            tokenRepository.save(accessToken);
        }

        if(jwtRefreshToken.equals("")){
            var refreshToken = RefreshToken.builder()
                .userinfo(user)
                .token(jwtRefreshToken)
                .expired(false)
                .build();
            refreshRepository.save(refreshToken);
        }
    }

    private void revokeAllUserTokens(Userinfo user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        var validRefreshTokens = refreshRepository.findAllValidTokenByUser(user.getId());

        if(validUserTokens.isEmpty()) return;
        
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);

        if(validRefreshTokens.isEmpty()) return;

        validRefreshTokens.forEach(token -> {
            token.setExpired(true);
        });

        refreshRepository.saveAll(validRefreshTokens);
    }


}