package com.likeurator.squadmania_auth.token;


import com.likeurator.squadmania_auth.domain.user.model.Userinfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//accessToken은 프론트엔드에서 발급 해야 함.
//token의 생성 자체는 API가 처리. 저장은 client에서
    //client에서 header로 accessToken을 받는다.
    //받은 accesstoken을 체크하여 refreshToken을 생성한다.
//해당 token의 entity는 임시 .
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "access_token")
public class AccessToken {
    
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "token", unique = true)  
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;
    
    private boolean revoked;
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Userinfo userinfo;

}