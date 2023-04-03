package com.likeurator.squadmania_auth.domain.user;

import java.util.List;

import com.likeurator.squadmania_auth.token.AccessToken;
import com.likeurator.squadmania_auth.token.RefreshToken;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateResponse {
    @Column(name = "email_id")
    private String emailId;

    @Column(name = "password")
    private String password;

    
}
