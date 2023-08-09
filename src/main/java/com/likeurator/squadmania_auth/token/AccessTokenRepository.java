package com.likeurator.squadmania_auth.token;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
    List<AccessToken> findAllValidTokenByUser(String id);
    Optional<AccessToken> findByToken(String token);
}