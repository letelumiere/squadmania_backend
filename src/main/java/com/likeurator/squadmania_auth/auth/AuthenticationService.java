package com.likeurator.squadmania_auth.auth;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final RefreshTokenRepository refreshRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = Userinfo.builder()
            .emailId(request.getEmail_id())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
        repository.save(user);

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(jwtToken);
        saveUserToken(savedUser, jwtToken, refreshToken);

        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build(); 
    }
    
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail_id(),
                request.getPassword()
            )
        );
        var user = repository.findByEmail(request.getEmail_id())
            .orElseThrow();
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(token);
        revokeAllUserTokens(user);
        saveUserToken(user, token, refreshToken);

        return AuthenticationResponse.builder().accessToken(token).refreshToken(refreshToken).build();
    }

    public void refresh(AuthenticationRequest request){
        
    }

    //토큰 저장 메서드 -> accessToken은 localStorage에 저장될 예정(?)
    //최초에는 
    private void saveUserToken(Userinfo user, String jwtToken, String jwtRefreshToken) {
        var accessToken = AccessToken.builder()
            .userinfo(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();

        tokenRepository.save(accessToken);

        var refreshToken = RefreshToken.builder()
            .userinfo(user)
            .token(jwtRefreshToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
        refreshRepository.save(refreshToken);
    }

    
    //logout처리용 메서드
    private void revokeAllUserTokens(Userinfo user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validUserTokens.isEmpty()) return;
        
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}