package com.likeurator.squadmania_auth.token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface AccessTokenRepository extends CrudRepository<AccessToken, UUID> {
    List<AccessToken> findAllValidTokenByUser(UUID id);
    Optional<AccessToken> findByToken(String token);

}