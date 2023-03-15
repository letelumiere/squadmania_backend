package com.likeurator.squadmania_auth.config;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import com.likeurator.squadmania_auth.domain.user.UserRepository;
import com.likeurator.squadmania_auth.token.AccessToken;
import com.likeurator.squadmania_auth.token.TokenRepository;
import com.likeurator.squadmania_auth.token.TokenType;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthentificationFilter extends OncePerRequestFilter{
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(!isAuthHeaderValid(authHeader) || isServletPathValid(request)){
            filterChain.doFilter(request, response);
        }else{
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            
            if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
                
                if((jwtService.isTokenValid(jwt, userDetails) && isTokenValid)){
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        }   
    }


    private boolean isAuthHeaderValid(String authHeader){
        if(authHeader!=null && authHeader.startsWith("Bearer ")) return true;
        return false;
    }

    private boolean isServletPathValid(HttpServletRequest request){
        if(request.getServletPath().startsWith("/api/v1/auth/refresh")) return true;
        return false;
    }
}
