
```
squadmania_auth
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ com
   │  │     └─ likeurator
   │  │        └─ squadmania_auth
   │  │           ├─ auth
   │  │           │  ├─ AuthenticationController.java
   │  │           │  ├─ AuthenticationService.java
   │  │           │  ├─ AuthorizationController.java
   │  │           │  ├─ AuthorizationService.java
   │  │           │  └─ model
   │  │           │     ├─ AuthenticationRequest.java
   │  │           │     ├─ AuthenticationResponse.java
   │  │           │     ├─ RegisterRequest.java
   │  │           │     └─ RestRequest.java
   │  │           ├─ config
   │  │           │  ├─ ApplicationConfig.java
   │  │           │  ├─ filter
   │  │           │  │  ├─ CustomAccessDeniedHandler.java
   │  │           │  │  ├─ CustomAuthenticationEntryPoint.java
   │  │           │  │  ├─ JwtAuthentificationFilter.java
   │  │           │  │  └─ JwtExceptionFilter.java
   │  │           │  ├─ JwtService.java
   │  │           │  ├─ LogoutService.java
   │  │           │  └─ SecurityConfiguration.java
   │  │           ├─ demo
   │  │           │  └─ DemoController.java
   │  │           ├─ domain
   │  │           │  └─ user
   │  │           │     ├─ model
   │  │           │     │  ├─ Userinfo.java
   │  │           │     │  ├─ UserinfoDate.java
   │  │           │     │  ├─ UserInfoId.java
   │  │           │     │  ├─ UserUpdateRequest.java
   │  │           │     │  └─ UserUpdateResponse.java
   │  │           │     ├─ Role.java
   │  │           │     ├─ UserController.java
   │  │           │     ├─ UserRepository.java
   │  │           │     └─ UserService.java
   │  │           ├─ ServletInitializer.java
   │  │           ├─ SquadmaniaAuthApplication.java
   │  │           └─ token
   │  │              ├─ AccessToken.java
   │  │              ├─ RefreshToken.java
   │  │              ├─ RefreshTokenRepository.java
   │  │              ├─ TokenRepository.java
   │  │              └─ TokenType.java
   │  └─ resources
   │     ├─ application.yml
   │     ├─ static
   │     └─ templates
   └─ test
      └─ java
         └─ com
            └─ likeurator
               └─ squadmania_auth
                  ├─ RegisterTest.java
                  └─ SquadmaniaAuthApplicationTests.java

```

