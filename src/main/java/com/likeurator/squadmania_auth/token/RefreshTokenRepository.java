package com.likeurator.squadmania_auth.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, UUID>{
    @Query(value = """
        select r.*
            from refresh_token r inner join userinfo u
                on r.user_id = u.id
            where r.user_id = ? AND (r.expired='false');
    """, nativeQuery = true)
    List<RefreshToken> findAllValidTokenByUser(UUID id);

    @Query(value = """
        select r.*
            from refresh_token r inner join userinfo u
                on r.id = u.id
            where u.email_id = ?;
    """, nativeQuery = true)
    Optional<RefreshToken> findByUserEmail(String email);
    Optional<RefreshToken> findByToken(String token);

    @Query(value = """
        select r.*
            from refresh_token r inner join userinfo u 
            where r.user_id = u.id and u.email_id = ?
            order by r.id desc limit 1; 
        """, nativeQuery = true)
    Optional<RefreshToken> findRefreshTokenByUsername(String email);
}
