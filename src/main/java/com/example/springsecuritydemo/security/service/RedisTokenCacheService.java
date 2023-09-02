package com.example.springsecuritydemo.security.service;

import com.example.springsecuritydemo.security.exception.TokenCacheIsDownException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

//@Service
//@RequiredArgsConstructor
public class RedisTokenCacheService implements JwtTokenCache {

    //private final StringRedisTemplate redisTemplate;

    @Override
    public Optional<Set<GrantedAuthority>> getAuthoritiesByLogin(String jti) throws TokenCacheIsDownException {
        return Optional.empty();
    }

    @Override
    public void putAuthoritiesByLogin(String jti, Set<GrantedAuthority> authorities) {

    }

    @Override
    public void putJtiByLogin(String login, String jti) {

    }

    @Override
    public Optional<String> getJtiByLogin(String login) throws TokenCacheIsDownException {
        return Optional.empty();
    }

    @Override
    public void removeSecurityCacheByLogin(String login) {
        //redisTemplate.delete(login);

    }
}
