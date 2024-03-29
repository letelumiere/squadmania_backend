package com.likeurator.squadmania_auth.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.likeurator.squadmania_auth.config.filter.JwtAuthentificationFilter;
//import com.likeurator.squadmania_auth.domain.oauth2.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration(proxyBeanMethods = false) 
@EnableWebSecurity 
@RequiredArgsConstructor
@ConditionalOnDefaultWebSecurity 
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfiguration {
    
    private final JwtAuthentificationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    //private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .formLogin(FormLoginConfigurer<HttpSecurity>::disable);
        http.authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/demo-controller/**").permitAll()
                .requestMatchers("/api/v1/user/**").permitAll()
                .requestMatchers("/api/v1/oauth2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));
            /*
                .oauth2Login(login -> login
                        .clientRegistrationRepository(clientRegistrationRepository())
                        .authorizedClientRepository(authorizedClientRepository())
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService));
             */                        
        return http.build();
    }
/*
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(){
        return new InMemoryClientRegistrationRepository(this.kakaoClientRegistration());
    }
    
    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository() {
        return new HttpSessionOAuth2AuthorizedClientRepository();
    }
    
    private ClientRegistration kakaoClientRegistration(){
        return ClientRegistration.withRegistrationId("kakao")
            .clientId("f66ad78db368781970e4086debb56661")
            .clientSecret("y4Rv3gbKYIJdcyLZbtY6VGVnLdlhnkY7")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri("{baseUrl}/api/v1/oauth2/code/{registrationId}")
            .scope("account_email", "profile_nickname", "profile_image", "openid")
            .authorizationUri("https://kauth.kakao.com/oauth/authorize")
            .tokenUri("https://kauth.kakao.com/oauth/token")
            .userInfoUri("https://kapi.kakao.com/v2/user/me")
            .userNameAttributeName("id")
            .clientName("kakao")
            .build();
            //.addFilterBefore(jwtExceptionFilter, JwtAuthentificationFilter.class)
            //.exceptionHandling()
            //.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            //.and()

    }
*/
}

