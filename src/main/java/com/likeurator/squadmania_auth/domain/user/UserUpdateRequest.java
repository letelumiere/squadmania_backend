package com.likeurator.squadmania_auth.domain.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "password")
    private String password;

}
