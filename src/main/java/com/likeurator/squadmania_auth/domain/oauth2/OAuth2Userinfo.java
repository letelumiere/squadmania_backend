package com.likeurator.squadmania_auth.domain.oauth2;

import java.util.Map;

import lombok.Getter;

@Getter
public abstract class OAuth2Userinfo {
    protected Map<String, Object> attributes;

    public OAuth2Userinfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    
    public Map<String, Object> getAttributes(){
        return attributes;
    }
    
    public abstract String getId();
    public abstract String getName();
    public abstract String getEmail();
    public abstract String getImageUrl();
}
