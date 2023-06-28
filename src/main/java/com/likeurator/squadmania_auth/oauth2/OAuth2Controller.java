package com.likeurator.squadmania_auth.oauth2;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.likeurator.squadmania_auth.auth.model.RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {
    CustomOAuth2UserService oauth2SUserService;

    //http://localhost:8080/api/v1/auth/login/oauth2/code/kakao
    //https://kapi.kakao.com/v2/user/me		
    //http://localhost:8080/login/oauth2/code/kakao
    //@GetMapping("/oauth2/code/kakao")
    //public ResponseEntity<RegisterRequest> kakaoLogin(RegisterRequest request){
    //    return 
    //}
    //@GetMapping("/api/v1/auth/login/oauth2/code/loaduser")
    //public ResponseEntity<OAuth2User> loaduser(RegisterRequest request){
        
//	private final ClientRegistration clientRegistration;
//	private final OAuth2AccessToken accessToken;
//	private final Map<String, Object> additionalParameters;
//        OAuth2UserRequest userRequest = new OAuth2UserRequest(null, null, null);
//        return ResponseEntity.ok(oauth2SUserService.loadUser(userRequest));
//    }
}
