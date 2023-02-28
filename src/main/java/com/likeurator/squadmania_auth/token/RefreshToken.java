package com.likeurator.squadmania_auth.token;

import com.likeurator.squadmania_auth.domain.user.Userinfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//client에서 header로 accessToken을 받는다.
//받은 accesstoken을 체크하여 refreshToken을
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "refresh_token", unique = true)  //token발급 기간이 expired 되었는지 여부
    public String refreshToken;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;
    public boolean revoked;
    public boolean expired;

    @OneToOne
    @JoinColumn(name = "token")
    public Token token;
}