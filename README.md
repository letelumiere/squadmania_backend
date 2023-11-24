
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
  해당 리포지토리는 iOS 앱 서비스 <a href="http://squadmania.github.io">스쿼드매니아</a>의 백엔드 프로젝트의 소스 코드를 다루고 있습니다.

### 프로젝트 소개
  스쿼드매니아는 넥슨 모바일에서 퍼블리싱한 게임 <피파 온라인4>의 전적 검색 서비스로서, 지난 2021년에 애플스토어에 출시 된 이래 안정적인 운영을 이어나가고 있는 게임 데이터 서비스 입니다. 사용자 경험과 접근성 면에서 무난한 평가를 받고 있지만, 서비스 내외적으로 여러 애로사항이 가 있었습니다. 개인적으로 신입 백엔드 개발자를 준비하는 입장에서 전체적으로 접하고 숙달해야 할 기술들이 사용되는 프로젝트 였기에 참여하게 되었습니다.

### 프로젝트의 기획

### 개발 목표
  - 현재 서비스 환경에서는 외부 API과의 요청 응답과 DB 처리 과정 등의 모든 기능들이 iOS에 설치된 어플 내부에서 동작함. 이로인해 서비스가 유지될수록 어플의 용량이 증가하며, 사용자의 모바일 환경에서 메모리 부담이 커질 수 밖에 없었습니다.
  - 한편 서비스의 모든 기능이 Swift와 node.js의 기반 하에 개발 되었는데, 이 때문에 android, HTML5과 같은 타 플랫폼 사용자들을 확보하는데 제약을 겪었으며, 추후 서비스 확대 과정에서 광범위한 어려움을 겪게 될 것이 분명했습니다.
  - 해당 프로젝트는 위의 애로사항들을 해결하기 위한 웹 서버 어플리케이션의 개발 프로젝트로서, 기존의 모바일 앱 환경에서 사용되는 기능들을 웹 서버에서 지원하는 한편, 기존의 서비스에서 기술적으로 미약한 점을 보완하는데 주안점을 두고 진행하는 것으로 방향성을 삼았습니다. 

### 개발 환경

#### 1. Language
  - Java SE-17

#### 2. Framework/Library
  - Springboot 3.0.2
  - SpringSecurity
  - SpringOAuth2.0
  - SpringJPA with Hibernates
  
#### 3. BuildTool
  - Gradle7.6

#### 4. Database
  - MySQL CommunityEdition 8.0.19.
  - RedisInsight-v2 2.30.0

#### 5. CI/CD
  - Amazon EC2 Instance
  - Amazon RDS
  - Docker version 24.0.5, build ced0996
  - github-actions
  - nginx

---

### 프로젝트 패키지의 구조
<details>
  <summary>해당 웹 어플리케이션의 패키지 트리는 다음과 같습니다</summary>
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
#### 1. 회원 정보와 관련된 CRUD 처리 기능의 REST API 구현
  - 현재 모바일 앱 내부에서 동작 하는 회원 가입, 탈퇴, 정보 수정 기능을 서버에 구현.
  - 파라미터 간의 조합으로 이루어지는 회원 고유번호 생성 기능을 자동화 함. 해당 비즈니스 로직의 생산성이 20% 향상.
  - 개인 정보와 관련된 데이터는 SHA-256에 기반한 자동 암호화 생성 기능을 도입. 현행 기능 대비 보안 효율이 120% 상승.
   
#### 2. 인증/인가 절차와 관련된 REST API 구현
  - 현재 모바일 앱 내부에서 동작 하는 로그인, 로그아웃 기능을 서버에 구현.
  - 현행 서비스 내에서 RDBMS가 관여하는 로그인 여부를 개선.
  - Json Web token 발급과 OAuth2.0을 통한 외부 인증/인가 처리를 구현.

#### 3. 데이터베이스의 최적화와 테이블 간 정규화
  - 회원 정보 관련 테이블 'userinfo'간 관계와 PK/SK를 세부 설정.
  - JPA Hibernates의 ORM 구현체를 통해, 파라미터의 일치 여부로만 판별 되던 테이블 간 연관 관계를 구축.
  - 기존 'userinfo' 테이블에서 boolean으로 로그인 여부를 처리하는 'login', 'logout' 컬럼을 삭제함. 또한 'userinfo' 테이블의 컬럼 일부를 'userinfo_date'로 정규화하여 테이블 간에 상속 관계를 설정함.
  - 로그인 처리시 업데이트 되는 시간과 토큰의 유효기간을 대조하는 로직으로 개선함.
  - 위의 개선 사항들을 통해 데이터베이스 개선 작업을 통해 30%의 생산성을 가져옴.

#### 4. 웹 서버 어플리케이션의 자체적인 토큰 발급 기능을 구현
  - 서버 자체적으로 SHA-256 기반의 암호화 토큰을 생성하여 인증/인가 과정에 사용.
  - 현재의 단일한 토큰 체계를 accessToken과 refreshToken으로 이원화. 기존 대비 보안 효율성 40% 개선.
     
#### 5. 새로운 데이터베이스 모델링의 도입
  - 기존의 비즈니스 로직 하에서 토큰 저장에 사용된 MySQL를 Redis로 대체함. 
  - 토큰 처리에 관계형 데이터베이스 조회의 빈도를 줄임으로서 해당 기능의 효율성에 있어 약 20%의 개선을 예상함.

#### 6. 테스트 및 배포에 필요한 서버 구축과 워크 플로우 개선
   - AWS EC2 Instance와 AWS RDS를 연계하여 서버 환경을 구축.
   - docker container를 도입해 개발 및 배포 환경의 통일 함.
   - docker-compose와 github-action을 통해 웹 서버 어플리케이션 패키지 생성 및 서버 배포 과정을 자동화 함.
    
---
### 추후 구현 예정인 사항
#### 1. 기존 모바일 앱에서 지원하는 REST API의 추가 및 개선 작업
  - 회원 가입 비즈니스 로직에 이메일 인증 추가
  - 기존의 회원 정보 테이블에서 관리자 정보를 분리하여 해당하는 REST API를 추가
  - OAuth2.0의 인증 체계 다양화
#### 2. 코드의 유지 보수 및 리펙토링
  - 개별 트랜잭션에서 발생하는 에러와 예외 처리 디버깅을 위한 handler 구현
#### 3. 데이터 모델링의 다양화
  - 외부 게임 데이터 API에 적합한 NoSQL 도입
  - 외부 게임 데이터 API의 대용량 트랜잭션 처리와 자동화 기능 도입
#### 4. 서버 구축 및 자동화 배포 과정
  - git 리포지토리의 자동화 배포 디버그 및 유지 보수 과정에서 발생하는 비효율을 개선
  - iOS앱 패키지 git의 릴리즈 자동화를 개선
  - 운영 중인 서비스에 해당 개발 사항을 적용
  
----
### 개발 기록
  - <a href="https://velog.io/@letelumiere/squadmania-dev-1" target="_blank"> 프로젝트의 간략한 소개</a>
  - <a href="https://velog.io/@letelumiere/squadmania-dev-2" target="_blank"> Spring Security의 Congifuration과 authentificationFilter 구축</a>
  - <a href="https://velog.io/@letelumiere/squadmania-dev-3" target="_blank"> authntificiaionConfiguration 설정</a>
  - <a href="https://velog.io/@letelumiere/squadmania-dev-4" target="_blank"> JPA with Hibernates를 통한 데이터 영속성 부여 과정</a>
  - <a href="https://velog.io/@letelumiere/squadmania-dev-5" target="_blank"> 회원 가입, 탈퇴 및 정보 수정 기능 구현</a>
  - <a href="https://velog.io/@letelumiere/squadmania-dev-6" target="_blank"> 로그인, 로그아웃, Json Web Token 발급 및 유효기간 기능 구현</a>
  - <a href="https://velog.io/@letelumiere/squadmania-dev-7" target="_blank"> 카카오 서비스를 통한 OAuth2.0 인증 도입 과정</a>
  - <a href="https://velog.io/@letelumiere/squadmania-dev-8" target="_blank"> Json Web Token의 유효성 체크를 위한 Redis 도입 과정</a>
---
