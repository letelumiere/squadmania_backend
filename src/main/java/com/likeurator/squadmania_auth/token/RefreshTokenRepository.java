package com.likeurator.squadmania_auth.token;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, UUID>{
    List<RefreshToken> findAllValidTokenByUser(UUID id);
    Optional<RefreshToken> findByUserEmail(String email);
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findRefreshTokenByUsername(String email);
}
