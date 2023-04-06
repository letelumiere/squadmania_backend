package com.likeurator.squadmania_auth.auth.model;

import java.util.Date;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestRequest {
    @Column(name="email_id")
    private String email_id;
        
    @Column(name="password")
    private String password;
}
