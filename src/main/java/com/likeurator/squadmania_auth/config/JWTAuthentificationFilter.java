package com.likeurator.squadmania_auth.config;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.likeurator.squadmania_auth.token.TokenRepository;
import com.likeurator.squadmania_auth.token.RefreshTokenRepository;

import com.nimbusds.oauth2.sdk.RefreshTokenGrant;

import io.jsonwebtoken.Jwts;


@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthentificationFilter extends OncePerRequestFilter{
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
        }else{
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
    

            if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
                
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                //case1 : access token과 refresh token 모두가 만료된 경우 → 에러 발생 (재 로그인하여 둘다 새로 발급)
                var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

                var isRefreshExpired = refreshTokenRepository.findByUserEmail(userEmail)
                    .map(t -> !t.isExpired())
                    .orElse(false);

                //case3 : access token은 유효하지만, refresh token은 만료된 경우 →  access token을 검증하여 refresh token 재발급                    
                if(!isRefreshExpired) jwtService.generateRefreshToken(userDetails);

                //case2 : access token은 만료됐지만, refresh token은 유효한 경우 →  refresh token을 검증하여 access token 재발급
                if(!isTokenValid) jwtService.generateToken(userDetails);

                //case4 : access token과 refresh token 모두가 유효한 경우 → 정상 처리
                if(jwtService.isTokenValid(jwt, userDetails) && isTokenValid){
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(userDetails,
                            null,
                            userDetails.getAuthorities()
                    );             
                    authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }    
            }
            filterChain.doFilter(request, response);
        }   
    }    
}
