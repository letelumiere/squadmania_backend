package com.likeurator.squadmania_auth.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
    List<RefreshToken> findAllValidTokenByUser(Long id);
    Optional<RefreshToken> findByToken(String token);

}
