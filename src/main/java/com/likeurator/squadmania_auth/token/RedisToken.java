package com.likeurator.squadmania_auth.token;


import org.springframework.data.redis.core.RedisHash;

import com.likeurator.squadmania_auth.domain.user.model.Userinfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "redis_token", timeToLive = 30)
public class RedisToken {
    
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "token", unique = true)  
    private String token;
    
    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;
    
    private boolean revoked;
    private boolean expired;
    private Userinfo userinfo;
}