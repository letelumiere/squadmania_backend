package com.likeurator.squadmania_auth.domain.oauth2;

import com.likeurator.squadmania_auth.domain.user.model.Userinfo;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class SessionUser {
	private String name;
	private String email;
	private String profile_yn;
    
    public SessionUser(Userinfo user) {
        this.name = user.getUsername();
        this.email = user.getEmailId();
        this.profile_yn = user.toString();
    }
}