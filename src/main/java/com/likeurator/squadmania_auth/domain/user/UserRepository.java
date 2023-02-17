package com.likeurator.squadmania_auth.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<Userinfo, Long>{

    @Query(value = "select * from userinfo where email_id = ?",
        nativeQuery = true)
    public Optional<Userinfo> findByEmail(String email_id);

}
