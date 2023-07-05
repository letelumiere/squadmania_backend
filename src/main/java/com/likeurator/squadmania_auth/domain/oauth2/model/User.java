package com.likeurator.squadmania_auth.domain.oauth2.model;

import com.likeurator.squadmania_auth.domain.user.Role;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class User {
    String name;
    String email;
    String picture;
    Role role;
}
