package com.likeurator.squadmania_auth.config;

import java.io.IOException;


import com.likeurator.squadmania_auth.auth.*;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;



@Component
@RequiredArgsConstructor
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, JwtException {
        try{
            filterChain.doFilter(request, response);
        }catch(IllegalArgumentException e){
            log.error("400 bad Request!");
        }catch(ExpiredJwtException e){
            log.error("401 unathorized Request! Jwt Token Expired!");
        }catch(SignatureException e){
            log.error("402 payment Request!");
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable e) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
        JwtException jwtException = new JwtException(e.getMessage(), e);
        
        JSONParser parser = new JSONParser(jwtException.toString());
            
        response.getWriter().write(parser.hashCode());
    }
}
