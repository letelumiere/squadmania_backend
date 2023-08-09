package com.likeurator.squadmania_auth.token;

import lombok.*;

@Data
@Builder
public class UserTokens {
    private String id;
    private String accessToken;
    private String refreshToken;
}
