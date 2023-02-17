package com.likeurator.squadmania_auth.domain.user;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.GeneratedColumn;
import org.hibernate.annotations.ListIndexBase;
import org.hibernate.tuple.GenerationTiming;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Indexed;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Id 
	@Column(name ="id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
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
    private Date loginDate;

    @Column(name = "login_rest_date")
    private Date loginRestDate;

    @Enumerated(EnumType.STRING)
    private Role role;

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
