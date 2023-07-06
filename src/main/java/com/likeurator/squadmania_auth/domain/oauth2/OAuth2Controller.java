package com.likeurator.squadmania_auth.domain.oauth2;

import java.util.Base64;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.likeurator.squadmania_auth.auth.model.RegisterRequest;
import com.nimbusds.jose.shaded.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//참고 url
//https://accounts.kakao.com/login/?continue=https%3A%2F%2Fkauth.kakao.com%2Foauth%2Fauthorize%3Fscope%3Daccount_email%2520profile_nickname%2520profile_image%2520openid%26response_type%3Dcode%26state%3Dj-Fw5WxVA-pSMDTjhVWmDnrb5OCFOM9uPVlNOfT0B_w%253D%26redirect_uri%3Dhttp%253A%252F%252Flocalhost%253A8080%252Fapi%252Fv1%252Foauth2%252Fcode%252Fkakao%26through_account%3Dtrue%26nonce%3D1LFGBco_otbu0emfcFyBjxoN8b4joityN7WxKyaKoA8%26client_id%3Df66ad78db368781970e4086debb56661#login
//http://localhost:8080/api/v1/oauth2/code/kakao?code=rNVIDULG56fOl_O3CiUmrk74XkStgdAueG8mFFusNGwKizXQzXgM97PK7gM3RSQodJ-CtwopyNoAAAGJJO5JtQ&state=j-Fw5WxVA-pSMDTjhVWmDnrb5OCFOM9uPVlNOfT0B_w%3D

@RestController
@RequestMapping("api/v1/oauth2")
@Slf4j
public class OAuth2Controller {

    @Autowired
    private CustomOAuth2UserService oAuth2UserService;
    
    @GetMapping("/code/kakao")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {
        return ResponseEntity.ok(oAuth2UserService.kakaoCallback(code));
    }
}