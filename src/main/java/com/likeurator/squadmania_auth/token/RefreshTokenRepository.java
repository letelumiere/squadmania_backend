package com.likeurator.squadmania_auth.token;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String>{
    List<RefreshToken> findAllValidTokenByUser(String id);
    Optional<RefreshToken> findByUserEmail(String email);
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findRefreshTokenByUsername(String email);
}
