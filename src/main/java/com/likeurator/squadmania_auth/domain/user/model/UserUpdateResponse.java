package com.likeurator.squadmania_auth.domain.user.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
