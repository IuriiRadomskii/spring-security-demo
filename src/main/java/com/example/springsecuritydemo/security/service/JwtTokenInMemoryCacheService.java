package com.example.springsecuritydemo.security.service;

import com.example.springsecuritydemo.security.exception.TokenCacheIsDownException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class JwtTokenInMemoryCacheService implements JwtTokenCache {

    private final Map<String, Set<GrantedAuthority>> cache = new HashMap<>();
    private final Map<String, String> jtiCache = new HashMap<>();

    @Override
    public Optional<Set<GrantedAuthority>> getAuthoritiesByLogin(String login) {
        var retval = this.cache.get(login);
        log.info("getAuthoritiesByJti: {}, {}", login, retval);
        return Optional.ofNullable(retval);
    }

    @Override
    public void putAuthoritiesByLogin(String login, Set<GrantedAuthority> authorities) {
        log.info("putAuthoritiesByJwtToken: {}, {}", login, authorities);
        cache.put(login, authorities);
    }

    @Override
    public void putJtiByLogin(String login, String jti) {
        log.info("putJtiByLogin: {}, {}", login, jti);
        jtiCache.put(login, jti);
    }

    @Override
    public Optional<String> getJtiByLogin(String login) throws TokenCacheIsDownException {
        return Optional.ofNullable(jtiCache.get(login));
    }

    @Override
    public void removeSecurityCacheByLogin(String login) {
        jtiCache.remove(login);
        cache.remove(login);
    }


}
