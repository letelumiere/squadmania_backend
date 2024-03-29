package com.likeurator.squadmania_auth.config.filter;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;



import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, JwtException {
        try{
            filterChain.doFilter(request, response);
        }catch(IllegalArgumentException e){
            setErrorResponse(HttpStatus.BAD_REQUEST, response, e);

        }catch(ExpiredJwtException e){
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e);

        }catch(SignatureException e){
            setErrorResponse(HttpStatus.PAYMENT_REQUIRED, response, e);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable e) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        
        response.getWriter().write(objectMapper.writeValueAsString(e));
    }
}
