package com.likeurator.squadmania_auth.domain.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.likeurator.squadmania_auth.domain.user.model.Userinfo;

public interface UserRepository extends JpaRepository<Userinfo, Long>{

    @Query(value = "select * from userinfo where email_id = ?;",
        nativeQuery = true)
    public Optional<Userinfo> findByEmail(String email);

    @Query(value = "select * from userinfo where id = ?;",
        nativeQuery = true)
    public Optional<Userinfo> getReferenceByUUID(UUID id);

    @Query(value = "select * from userinfo where withdraw_date = ?;",
        nativeQuery = true)
    public List<Userinfo> isWithdraws(Date date);

    @Query(value = "select * from userinfo where email_id = ? , provider= ?;",
            nativeQuery = true)
    public Optional<Userinfo> findByEmailAndProvider(String emailId, String provider);

}
