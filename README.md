
## 목차
---
---
- [들어가기에 앞서](#들어가기에-앞서)
- [프로젝트 소개](#프로젝트-소개)
  - [개발 환경](#개발-환경)
    - [Language](#Language)
    - [Framework/Library](#Framework/Library)
    - [BuildTool](#BuildTool)
    - [Database](#Database)
    - [Server/release](#Server/release)
  - [패키지 구조](#패키지-구조)
- [프로젝트의 기획 및 진행](#프로젝트의-기획-및-진행)
  - [개발 요구 사항](#개발-요구-사항)
  - [진행 상황](#진행-상황)
  - [추후 구현 예정인 사항](#추후-구현-예정인-사항)
- [개발 기록](#개발-기록)
- [후기](#후기)

---
---
### 들어가기에 앞서
해당 리포지토리는 iOS 앱 서비스 <a href="http://squadmania.github.io">스쿼드매니아</a>의 백엔드 프로젝트의 소스 코드를 다루고 있습니다.

### 프로젝트 소개
스쿼드매니아는 넥슨 모바일에서 퍼블리싱한 게임 <피파 온라인4>의 전적 검색 서비스로서, 지난 2021년에 애플스토어에 출시 된 이래 안정적인 운영을 이어나가고 있는 게임 데이터 서비스 입니다. 사용자 경험과 접근성 면에서 무난한 평가를 받고 있지만, 서비스 내외적으로 여러 애로사항이 가 있었습니다. 개인적으로 신입 백엔드 개발자를 준비하는 입장에서 전체적으로 접하고 숙달해야 할 기술을 적용해야 하는 프로젝트였기에 시작하게 되었습니다.



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

### 패키지 구조
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
### 프로젝트의 기획 및 진행

#### 개발 요구 사항
1. 프론트엔드와 백엔드의 분리
이슈 파악
   - 현행 서비스에서의 비즈니스 로직이 전적으로 iOS에 설치된 어플 내부에서 동작함
   - 서비스가 지속될수록 앱 패키지의 용량이 증가할 수 밖에 없는 구조
   - 모든 기능 구현이 Swift를 기반 하에 개발되었기에 사용자 확보에도 한계
방향
   - 백엔드를 담당하는 웹 어플리케이션 서버를 구축해 데이터 계층에 접근하는 트랜잭션을 전담하고, 앱 패키지의 부담을 절감시킬 것 
   - 프론트엔드 및 앱 플랫폼에 구애 받지 않는데 주안점을 둘 것
   - 철저하게 프론트엔드에서 요청하는 데이터 통신만 처리할 것
  
2. 현 서비스의 보안체계 개선
이슈 파악
 - OAuth 2.0의 기능이 카카오와 네이버 OAuth의 인증만 받음
 - 고유 식별 생성과 데이터 암호화 처리의 미흡함
 - 토큰 기반 인증/인가 서비스에서 나타나는 문제
방향
 - 해당 웹 어플리케이션에서 자체적으로 발급하는 OAuth2.0 체계의 구축
 - 어플리케이션 내부에서 자체적인 토큰 발급 체계를 구축
 - 개인 정보에 관한 암호화 생성 체계를 도입할 것
 

4. 대용량 처리 자동화 및 테이블 관계의 정규화
이슈 파악
- 외부에서 제공 받는 공식 게임 데이터 API의 문제로 운영 문제가 있음
- 관리자가 직접 데이터를 받아 서비스를 업데이트 하므로 운영 상 번거로움이 있음
- 사용자 경험과 밀접한 비즈니스 로직에서 속도 저하가 발생
- RDBMS 기반의 DB에서 테이블 관계의 정규화가 미흡함
방향
- 단일 회원관리 API에서 관리자 API를 분리하여 기능을 구현할 것
- 객체 지향 프로그래밍에 충실할 것
- 관계형 데이터베이스에 얽메이는 데이터 모델링에 벗어나 다양한 모델링을 도입할 것
- SpringBatch를 도입해 해당 서비스를 자동화
- 위 이슈를 해결함으로서 서비스 운영에 퍼포먼스를 향상시킴을 중점에 둘 것
 

---
### 진행 상황
- 백엔드 앱 어플리케이션 개발
  - Authentication/Authorization 기능의 개선
    - 회원 정보 테이블에 고유 식별자(UUID)에 자동 생성 기능 구현 
    - 회원의 민감한 정보에 암호화 자동생성 기능 도입
    - 웹 어플리케이션 내에 자체적인 Json Web Token 생성 트랜잭션 구현
    - 일원화된 토큰 보안 체계를 액세스 토큰과 리프레시 토큰으로 이원화
    - 액세스 토큰과 리프레시 토큰의 재발급과 유효성 검사 실시
    - SpringSecurity의 configuration을 적극 사용해 체계적인 권한 및 인증 처리 기능 구축
  - REST API 개선을 통해 기능 구현
    - JPA Hibernates를 도입해 데이터 객체 간 관계를 더욱 구체화함 
    - jwt를 통해 회원 가입, 정보 수정, 탈퇴 API 구현
    - jwt 기반으로 로그인 로그아웃 API 구현
    - Redis를 도입하여 jwt의 보관, 유효성에 관여하는 Hash 모델링 도입함 
  - 서버 구축 및 배포
    - 백엔드 기능을 전담하는 Amazon EC2 및 RDS 인스턴스 구축 및 연계
    - docker, github-action을 통한 개발 패키지 배포의 자동화 도입
---
### 추후 구현 예정인 사항

- 백앤드 앱 어플리케이션 개발
   - REST API의 추가 및 개선
     - 이메일 인증 코드 발송 기능의 추가
     - 관리자 정보를 담당하는 API의 추가
     - 자체 서비스의 OAuth2.0 인증 API 추가
   - 유지 보수 및 리펙토링
     - 각각의 트랜잭션에서 발생할 수 있는 예외 처리 코드를 추가하고 개선 할 것
     - 위 사항을 위한 디버깅을 위해 다양한 filter 모듈링을 추가
  - 데이터 모델링 도입의 다양화
    - 외부 API의 데이터 모델링에 맞춰 적절한 NoSQL을 도입
    - SpringBatch를 도입해 외부 API와의 대용량 트랜잭션 처리에 자동화 기능을 도입
- 서버 구축 및 자동화 배포 과정
  - 클라우드 서비스
  - Amazon ElasticCache의 도입
  - 자동화 배포 디버그 및 유지 보수 과정에서 발생하는 비효율을 개선
  - iOS앱 개발 파트의 배포 과정의 자동화 개선
  - 운영 중인 서비스에 해당 개발 사항을 적용
  
----
### 개발 기록
- <a href="https://velog.io/@letelumiere/squadmania-dev-1" target="_blank"> 프로젝트의 간략한 소개</a>
- <a href="https://velog.io/@letelumiere/squadmania-dev-2" target="_blank"> Spring Security의 Congifuration과 authentificationFilter 구축</a>
- <a href="https://velog.io/@letelumiere/squadmania-dev-3" target="_blank">authntificiaionConfiguration 설정</a>
- <a href="https://velog.io/@letelumiere/squadmania-dev-4" target="_blank">
JPA with Hibernates를 통한 데이터 영속성 부여 과정</a>
- <a href="https://velog.io/@letelumiere/squadmania-dev-5" target="_blank">
회원 가입, 탈퇴 및 정보 수정 기능 구현</a>
- <a href="https://velog.io/@letelumiere/squadmania-dev-6" target="_blank">
로그인, 로그아웃, Json Web Token 발급 및 유효기간 기능 구현</a>
- <a href="https://velog.io/@letelumiere/squadmania-dev-7" target="_blank">
카카오 서비스를 통한 OAuth2.0 인증 도입 과정</a>
- <a href="https://velog.io/@letelumiere/squadmania-dev-8" target="_blank">
Json Web Token의 유효성 체크를 위한 Redis 도입 과정</a>
---

### 후기
- 팀원은 2명이지만, 위 사항의 개발은 전적으로 제 스스로가 도맡아하는 1인 개발이다보니 시행착오가 많았습니다. 
- 그래도 1인인 덕분에 많은 것을 시도해 볼 수 있었던 것 같다. 
- 개발에서 AWS를 사용하는 것은 편리하고 좋지만, 요금 문제에서 실수 할 수 있다. 주의해야 한다.
- 개발 기간 동안 개발 외적인 요소도 충분히 고려해야 한다.(멘탈, 가정 문제 등)
- 내가 안정적인 기반 하에 있었으면 재밌었을 것 같다. 
- 체계의 중요성을 알게 됐다(?)
- 아직 미완인 코드지만 많은 성취를 남겼기에 애착이 있다. 시간 나는대로 이어가고 싶다.
