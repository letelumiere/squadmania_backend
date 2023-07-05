package com.likeurator.squadmania_auth.domain.oauth2.model;

import java.util.*;

import com.likeurator.squadmania_auth.domain.user.Role;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
public class OAuthAttributes {
    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes, String nameAttributeKey, String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    //사용자 정보는 Map이기 때문에 변경해야함
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String,Object> attributes){
        //네이버 로그인 인지 판단.
        if("naver".equals(registrationId)){
        return ofNaver("id",attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
    // 응답 받은 사용자의 정보를 Map형태로 변경.
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");
    // 미리 정의한 속성으로 빌드.
    return OAuthAttributes.builder()
    .name((String) response.get("name"))
    .email((String) response.get("email"))
    .picture((String) response.get("profile_image"))
    .attributes(response)
    .nameAttributeKey(userNameAttributeName)
    .build();
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String,Object> attributes){
    // 미리 정의한 속성으로 빌드.
    return OAuthAttributes.builder()
    .name((String) attributes.get("name"))
    .email((String) attributes.get("email"))
    .picture((String) attributes.get("picture"))
    .attributes(attributes)
    .nameAttributeKey(userNameAttributeName)
    .build();

    }

}

