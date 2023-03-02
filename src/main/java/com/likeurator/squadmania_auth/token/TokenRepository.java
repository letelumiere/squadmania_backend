package com.likeurator.squadmania_auth.token;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

public interface TokenRepository extends JpaRepository<AccessToken, Long> {
    @Query(value = """
        select t.*
            from access_token t inner join userinfo u
	            on t.id = u.id
            where u.id = t.user_id AND (t.expired='false' or t.revoked='false')
    """, nativeQuery = true)                    
    List<AccessToken> findAllValidTokenByUser(Long id);
    Optional<AccessToken> findByToken(String token);

    @Query(value = """
        select a.id, a.user_id, a.token as access_token, r.token as refresh_token 
            from access_token a join refresh_token r 
            where a.user_id = r.user_id;
    """, nativeQuery = true)
    Optional<AccessToken> findRefreshTokenByUser(Long id);

}