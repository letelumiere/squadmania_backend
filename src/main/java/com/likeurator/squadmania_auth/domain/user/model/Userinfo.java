package com.likeurator.squadmania_auth.domain.user.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.likeurator.squadmania_auth.domain.user.Role;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.tuple.GenerationTiming;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Data
@Entity(name = "userinfo")
@Table(name = "userinfo", 
        uniqueConstraints = {
                        @UniqueConstraint(name = "userinfo_id_unique", columnNames = {"id"})
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Userinfo implements UserDetails {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    
    @Column(name = "email_id")
    private String emailId;

    @Column(name = "auth_id")   
    private String authId;

    @Column(name = "auth_type")
    private String authType;

    @Column(name = "withdraw")
    private Boolean withdraw;

    @Column(name = "withdraw_done")
    private Boolean withdrawDone;

    @Column(name = "login_rest")
    private Boolean loginRest;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    
    @Column(name = "withdraw_date")
    private Date withdrawDate;

    @Column(name = "nickname_date")
    private Date nicknameDate;

    @Column(name = "login_date")
    @CurrentTimestamp(timing = GenerationTiming.ALWAYS)
    private Date loginDate;

    @Column(name = "login_rest_date")
    private Date loginRestDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "provider", length = 100)
    private String provider;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return emailId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
