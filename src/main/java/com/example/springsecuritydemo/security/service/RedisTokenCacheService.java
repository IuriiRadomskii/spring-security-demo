package com.example.springsecuritydemo.security.service;

import com.example.springsecuritydemo.security.exception.TokenCacheIsDownException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;
import java.util.Set;

public class RedisTokenCacheService implements JwtTokenCache {

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

    }
}
