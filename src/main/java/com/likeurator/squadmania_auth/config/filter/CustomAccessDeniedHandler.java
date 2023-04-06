package com.likeurator.squadmania_auth.config.filter;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) 
                throws IOException, ServletException {
    /*
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println("{ \"message\" : \"" + ErrorCode.ACCESS_DENIED.getMessage()
                + "\", \"code\" : \"" +  ErrorCode.ACCESS_DENIED.getCode()
                + "\", \"status\" : " + ErrorCode.ACCESS_DENIED.getStatus()
    q            + ", \"errors\" : [ ] }");

     */
    }
}