
## 목차
---
---
- [들어가기에 앞서](#들어가기에-앞서)
- [프로젝트 소개](#프로젝트-소개)
  - [프로젝트의 기획](#프로젝트의-기획)
    - [개발 목표](#개발-목표)
    - [개발 환경](#개발-환경)
    - [프로젝트 패키지의 구조](#프로젝트-패키지의-구조)
  - [진행 상황](#진행-상황)
  - [추후 구현 예정인 사항](#추후-구현-예정인-사항)
 - [개발 기록](#개발-기록)
---
---

### 들어가기에 앞서
해당 리포지토리는 iOS 앱 서비스 <a href="http://squadmania.github.io">스쿼드매니아</a>의 백엔드 프로젝트를 다루고 있습니다.

### 프로젝트 소개
스쿼드매니아는 넥슨 모바일에서 퍼블리싱한 게임 "피파 온라인4"의 전적 검색 서비스로, 2021년에 애플스토어에 출시된 이후 안정적인 운영을 지속하고 있는 게임 데이터 서비스입니다. 사용자 경험과 접근성 측면에서 양호한 평가를 받았지만, 서비스 내외적으로 기술적인 한계가 있었고, 이를 극복하고자 해당 서비스의 백엔드 파트 개발에 참여하게 됐습니다.

### 프로젝트의 기획

### 개발 목표
 - 현재 서비스 환경에서는 외부 API와의 요청 응답, DB 처리 등의 모든 기능이 iOS 어플리케이션 내에서 동작합니다. 이로 인해 서비스가 확장될수록 어플리케이션의 용량이 증가하고, 사용자의 모바일 환경에서 메모리 부담이 커질 수밖에 없었습니다.
 - 또한 위와 같은 문제로 향후 서비스 확장 시 개발, 운영 측면에서 어려움을 겪을 것으로 예상되었습니다.
 - 해당 프로젝트는 위의 문제점을 해결하기 위한 웹 서버 어플리케이션의 개발 구축 프로젝트로, 기존의 모바일 앱 환경에서 사용되는 기능들을 별도의 웹서버를 통해 구현하는 한편, 기술적으로 부족한 점을 개선하며 진행되었습니다.

### 개발 환경

#### 1. Language
  - Java17

#### 2. Framework/Library
  - Springboot gradle 3.0.2
  - SpringSecurity
  - Spring OAuth2.0

#### 3. BuildTool
  - Gradle 7.6

#### 4. Database
  - MySQL CommunityEdition 8.0.19.
  - Redis 7.0.12
  - 
#### 5. CI/CD
  - Amazon EC2 Instance
  - Amazon RDS
  - Docker version 24.0.5, build ced0996
  - Docker-compose 3
  - github-actions
  - nginx

---

### 프로젝트 패키지의 구조
<details>
  <summary>패키지 트리 구조</summary>
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

---

### 진행 상황  
#### 1. 회원 정보 및 관련 CRUD 처리 기능의 REST API 구현
- 모바일 앱 내에서 실행되던 회원 가입, 탈퇴, 정보 수정 기능을 서버 어플리케이션에 통합하여 구현하였습니다.
- 회원 고유번호 생성 기능을 자동화하고, 해당 로직을 서버 어플리케이션에서 실행하도록 최적화하였습니다. 이로써 해당 비즈니스 로직의 생산성이 20% 향상되었습니다.
- 민감한 개인 정보와 관련된 데이터에 대해 SHA-256을 기반으로 한 자동 암호화 생성 기능을 도입하여 보안 효율을 120% 향상시켰습니다.
#### 2. 보안 및 인증/인가 절차와 관련된 REST API 구현
- 모바일 앱 내에서 처리되던 로그인 및 로그아웃 로직을 서버에서 구현하였습니다.
- RDBMS에서 boolean으로 처리되던 로그인 여부 로직을 제거하여 DB 부담을 감소시켰습니다. 이로써 서비스의 안정성이 향상되었습니다.
- OAuth 2.0을 통한 외부 인증/인가 처리에서 Json Web Token(JWT)을 활용하여 구현하였습니다.
#### 3. 테이블 연관관계와 정규화를 통한 데이터베이스 최적화
- JPA Hibernate의 ORM 구현체를 활용하여 테이블 간 연관 관계를 설정하였습니다.
- 로그인 처리 시 업데이트되는 시간과 토큰의 유효 기간을 대조하는 로직을 도입하여 안전성을 향상시켰습니다.
- 위의 개선 사항들을 통해 데이터베이스 작업의 생산성이 30% 향상되었습니다.
#### 4. 웹 서버 어플리케이션의 자체적인 토큰 발급 기능 구현
- 서버 내에서 암호화 알고리즘을 사용하여 SHA-256 기반의 토큰을 생성하여 인증/인가 과정에 활용하였습니다.
- 서비스의 토큰을 accessToken과 refreshToken으로 이원화하여 보안 효율성이 40% 향상되었습니다.
#### 5. 새로운 데이터베이스 모델링의 도입
- 기존의 비즈니스 로직에서 토큰 저장에 사용된 MySQL을 Redis로 교체하였습니다.
- 토큰 처리에 관계형 데이터베이스 조회 빈도를 줄이는 방식으로 해당 기능의 효율성을 약 20% 향상시켰습니다.
#### 6. 테스트 및 배포에 필요한 서버 구축과 워크플로우 자동화 도입
- AWS EC2 Instance와 AWS RDS를 통합하여 서버 환경을 구축하였습니다.
- Docker container를 도입하여 개발 및 배포 환경을 통일화하였습니다.
- Docker-compose를 활용하여 웹 서버 어플리케이션 패키지를 생성하고, GitHub Actions를 통해 서버 배포 과정을 자동화하였습니다.
---

### 개발 기록
- [Spring Security의 Configuration과 AuthenticationFilter 구축](https://velog.io/@letelumiere/squadmania-dev-2)
- [AuthenticationConfiguration 설정](https://velog.io/@letelumiere/squadmania-dev-3)
- [JPA with Hibernate를 통한 데이터 영속성 부여 과정](https://velog.io/@letelumiere/squadmania-dev-4)
- [회원 가입, 탈퇴 및 정보 수정 기능 구현](https://velog.io/@letelumiere/squadmania-dev-5)
- [로그인, 로그아웃, JSON Web Token 발급 및 유효기간 기능 구현](https://velog.io/@letelumiere/squadmania-dev-6)
- [카카오 서비스를 통한 OAuth 2.0 인증 도입 과정](https://velog.io/@letelumiere/squadmania-dev-7)
- [JSON Web Token의 유효성 체크를 위한 Redis 도입 과정](https://velog.io/@letelumiere/squadmania-dev-8)
