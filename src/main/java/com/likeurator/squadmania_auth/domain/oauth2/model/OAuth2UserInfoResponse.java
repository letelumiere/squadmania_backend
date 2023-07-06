package com.likeurator.squadmania_auth.domain.oauth2.model;

import org.springframework.util.LinkedMultiValueMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2UserInfoResponse {
    private String accessToken;
    private String refreshToken;
    private String idToken;
}
