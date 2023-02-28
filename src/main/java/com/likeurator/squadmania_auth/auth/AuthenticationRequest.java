package com.likeurator.squadmania_auth.auth;

import jakarta.persistence.Column;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Column(name = "email_id")
    private String email_id;
    
    @Column(name = "password")
    private String password;
}

