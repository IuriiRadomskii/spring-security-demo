package com.example.springsecuritydemo.security.service;

import com.example.springsecuritydemo.security.exception.TokenCacheIsDownException;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;
import java.util.Set;

public class RedisTokenCacheService implements JwtTokenCache {

    @Override
    public Optional<Set<GrantedAuthority>> getAuthoritiesByJti(String jti) throws TokenCacheIsDownException {
        new RedisCache();
        return Optional.empty();
    }

    @Override
    public void putAuthoritiesByJti(String jti, Set<GrantedAuthority> authorities) {

    }
}
