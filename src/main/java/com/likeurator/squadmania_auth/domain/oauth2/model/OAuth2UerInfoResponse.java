package com.likeurator.squadmania_auth.domain.oauth2.model;

import org.springframework.util.LinkedMultiValueMap;

import lombok.Builder;

@Builder
public class OAuth2UerInfoResponse {
    private LinkedMultiValueMap<String, Object> parameters;
    private String accessToken;
    private String refreshToken;
    private String idToken;
    private String accountEmail;
}
