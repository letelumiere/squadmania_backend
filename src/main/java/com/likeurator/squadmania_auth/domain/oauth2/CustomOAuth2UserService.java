package com.likeurator.squadmania_auth.domain.oauth2;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.likeurator.squadmania_auth.domain.oauth2.model.OAuthAttributes;
import com.likeurator.squadmania_auth.domain.oauth2.model.User;
import com.likeurator.squadmania_auth.domain.user.UserRepository;
import com.likeurator.squadmania_auth.domain.user.model.Userinfo;
import com.nimbusds.jose.shaded.gson.Gson;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUser'");
    }

/*
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //DefaultOAuth2User 서비스를 통해 User 정보를 가져와야 하기 때문에 대리자 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        //네이버 로그인인지 구글로그인인지 서비스를 구분해주는 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //OAuth2 로그인 진행시 키가 되는 필드값 프라이머리키와 같은 값 네이버 카카오 지원 x
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                                        .getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService를 통해 가져온 데이터를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        //로그인 한 유저 정보
        User user = saveOrUpdate(attributes);

        //httpSession의 유저 속성을 설정
        httpSession.setAttribute("user", new SessionUser(user));
        // 로그인한 유저를 리턴함
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                    attributes.getAttributes(),
                    attributes.getNameAttributeKey());
    }

    //User 저장하고 이미 있는 데이터면 Update
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);    }
*/
}
