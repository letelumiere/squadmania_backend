package com.likeurator.squadmania_auth.auth;


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

import lombok.RequiredArgsConstructor;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail_id());

        var jwtRefreshToken = refreshRepository.findRefreshTokenByUsername(request.getEmail_id())
            .orElseThrow(null);

        String accessToken = jwtAccessToken.substring(7);
        String refreshToken = jwtRefreshToken.getToken();
        
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

        return AuthenticationResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    private void saveUserAccessToken(Userinfo user, String jwtToken) {
        var accessToken = AccessToken.builder()
            .id(user.getId())
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();

        tokenRepository.save(accessToken);
    }

    private void saveUserRefreshToken(Userinfo user, String jwtToken) {
        var refreshToken = RefreshToken.builder()
            .id(user.getId())
            .token(jwtToken)
            .build();

        refreshRepository.save(refreshToken);
    }    

    private void revokeAllUserTokens(Userinfo user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        var validRefreshTokens = refreshRepository.findAllValidTokenByUser(user.getId());

        if(!validUserTokens.isEmpty()){
            validUserTokens.forEach(token -> {
                tokenRepository.deleteAll(validUserTokens);
            });
        }
        
        if(!validRefreshTokens.isEmpty()){
            validRefreshTokens.forEach(token -> {
                refreshRepository.deleteAll(validRefreshTokens);
            });        
        }
    }
}