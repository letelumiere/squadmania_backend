package com.likeurator.squadmania_auth.token;


import java.util.UUID;

import org.springframework.data.redis.core.RedisHash;

import com.likeurator.squadmania_auth.domain.user.model.Userinfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "access_token")
public class AccessToken {
    
    @Id
    private UUID id;   
    
    @Column(name = "email_id")
    private String emailid;

    @Column(name = "token", unique = true)  
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;
    
    private boolean revoked;
    private boolean expired;
}