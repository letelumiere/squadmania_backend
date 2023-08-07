package com.likeurator.squadmania_auth;

import org.springframework.data.repository.CrudRepository;

import com.likeurator.squadmania_auth.token.RedisToken;

//Redis가 적용된 토큰 테스트를 위한 리포지토리
public interface RedisTokenRepository extends CrudRepository<RedisToken, Long>{

}

