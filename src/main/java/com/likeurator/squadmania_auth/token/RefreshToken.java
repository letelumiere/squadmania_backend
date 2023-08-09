package com.likeurator.squadmania_auth.token;


import java.util.UUID;

import org.springframework.data.redis.core.RedisHash;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "refresh_token", timeToLive = 60)
public class RefreshToken {

    @Id
    private UUID id;
    private String username;

    @Column(unique = true)
    public String token;
}