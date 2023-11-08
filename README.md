
## 목차
---
- [들어가기에 앞서](#들어가기에-앞서)
- [소개](#소개)
  - [프로젝트 설명](#프로젝트-설명)
  - [프로젝트 목표](#프로젝트-목표)
- [개발환경](#개발환경)
  - [Language](#Language)
  - [Framework/Library](#Framework/Library)
  - [BuildTool](#BuildTool)
  - [Database](#Database)
  - [App Container](#App-Container)
  - [Server/release](#Server/release)
- [패키지 구조](#패키지-구조)
- [개발 기록](#개발-기록)
- [보완점](#보완점)
- [보완해야 할 점](#보완해야-할-점)
- [후기](#후기)

---

### 들어가기에 앞서
---

### 소개
### 프로젝트 설명
해당 개발 일지는 iOS에서 서비스되는 앱, '스쿼드매니아'의 백엔드 파트의 개발 과정을 담고 있습니다.

### 프로젝트 목표
프로젝트의 목표는 다음과 같습니다

### 개발환경
---
#### Language
- Java SE-17

#### Framework/Library
- Springboot 3.0.2
- Spring Security
- Spring OAuth2.0
- Spring JPA
- Hibernates

### BuildTool
- Gradle

#### Database
- MySQL COmmunity 8.19.
- Redis

#### App Container
- Docker version 24.0.5, build ced0996
- nginx

#### Server/release
- AWS EC2 Instance
- AWS RDS
---

### 패키지 구조
<details>
  <summary>패키지 트리는 다음과 같습니다</summary>
  <div markdown="1">

    
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

  </div>
</details>

### 개발 기록
---
- [Spring Security와 authentication/authorization](#https://velog.io/@letelumiere/squadmania-dev-2)
- [annotation, bean, configuration](#https://velog.io/@letelumiere/squadmania-dev-3)
- [Java Persistance Application](#https://velog.io/@letelumiere/squadmania-dev-4)
- [REST API 구현과 테스트 (회원가입,정보수정,탈퇴)](#https://velog.io/@letelumiere/squadmania-dev-5)
- [session, token, cookie / 기능 구현 (로그인/로그아웃/토큰 발급/재발급)](#https://velog.io/@letelumiere/squadmania-dev-6)
- [카카오 OAuth2 등록 / 기능 구현](#https://velog.io/@letelumiere/squadmania-dev-7)
---

### 보완점
### 보완해야 할 점
### 후기


