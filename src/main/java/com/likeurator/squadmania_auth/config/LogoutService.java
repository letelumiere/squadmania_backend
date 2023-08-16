package com.likeurator.squadmania_auth.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.likeurator.squadmania_auth.token.AccessTokenRepository;
import com.likeurator.squadmania_auth.token.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) return;
        
        jwt = authHeader.substring(7);
        var storedToken = accessTokenRepository.findByToken(jwt)
            .orElse(null);

        if(storedToken != null){
            accessTokenRepository.delete(storedToken);

            var refreshToken = refreshTokenRepository.findByUserEmail(
                    storedToken.getEmailid()
                )
                .orElse(null);

            if(refreshToken != null) {
                refreshTokenRepository.delete(refreshToken);
            }

            SecurityContextHolder.clearContext();
        }
    }
}