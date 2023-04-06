package com.likeurator.squadmania_auth.auth.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Column(name="email_id")
    private String email_id;
    
    @Column(name="password")
    private String password;
}
