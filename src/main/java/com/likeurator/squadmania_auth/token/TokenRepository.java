package com.likeurator.squadmania_auth.token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<AccessToken, Long> {
    @Query(value = """
        select a.*
        from access_token a inner join userinfo u
            on a.user_id = u.id
        where a.user_id = ? AND (a.expired='false' or a.revoked='false');
    """, nativeQuery = true)                    
    List<AccessToken> findAllValidTokenByUser(@Param("u.id") UUID id);
    Optional<AccessToken> findByToken(String token);

}