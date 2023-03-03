package com.likeurator.squadmania_auth.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{


    @Query(value = """
        select r.*
            from refresh_token r inner join userinfo u
            on r.id =  u.id
        where u.email_id = ?
    """, nativeQuery = true)
    Optional<RefreshToken> findByUserEmail(String email);
    Optional<RefreshToken> findByToken(String token);
}
