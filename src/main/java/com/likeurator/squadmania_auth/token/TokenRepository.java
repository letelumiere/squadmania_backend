package com.likeurator.squadmania_auth.token;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = """
        select t.id, t.expired, t.revoked, t.token, t.tokenType, t.user_id
            from token t inner join userinfo u
	            on t.id = u.id
            where u.id = t.user_id AND (t.expired='false' or t.revoked='false')
    """, nativeQuery = true)                    
    List<Token> findAllValidTokenByUser(Long id);
    Optional<Token> findByToken(String token);
}