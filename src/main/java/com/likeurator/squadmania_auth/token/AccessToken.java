package com.likeurator.squadmania_auth.token;


import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class AccessToken implements Serializable {
    
    @Id
    private String id;   
    
    @Column(name = "email_id")
    private String emailid;

    @Column(name = "token")  
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    
    private boolean revoked;
    private boolean expired;
}