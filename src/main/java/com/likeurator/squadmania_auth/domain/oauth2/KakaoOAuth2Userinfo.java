package com.likeurator.squadmania_auth.domain.oauth2;

import java.util.Map;

import jakarta.persistence.*;
import lombok.*;

public class KakaoOAuth2Userinfo extends OAuth2Userinfo {
    public KakaoOAuth2Userinfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    @Override
    public String getEmail() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEmail'");
    }

    @Override
    public String getImageUrl() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getImageUrl'");
    }

    
}
