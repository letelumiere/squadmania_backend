package com.likeurator.squadmania_auth.domain.oauth2.model;

import java.util.Map;
import lombok.Getter;

public interface OAuth2Userinfo {
    Map<String, Object> getAttributes();
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
