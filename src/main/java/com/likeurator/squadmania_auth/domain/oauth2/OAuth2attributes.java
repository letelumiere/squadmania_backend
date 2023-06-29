package com.likeurator.squadmania_auth.domain.oauth2;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.likeurator.squadmania_auth.domain.user.Role;
import com.likeurator.squadmania_auth.domain.user.model.Userinfo;

import lombok.Builder;

@Builder
public class OAuth2attributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    
    public OAuth2attributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuth2attributes of(String socialName, String usernameAttributes, Map<String, Object> attributes){
        if("kakao".equals("socialName")) return ofkakao("id", attributes);
        else return null;
    }

    private static OAuth2attributes ofkakao(String usernameAttributes, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuth2attributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .nameAttributeKey(usernameAttributes)
                .attributes(attributes)
            .build();
    }
    public Userinfo toEntity(){
        return Userinfo.builder()
                .emailId(email)
                .role(Role.USER)
            .build();
    }
}   

    
