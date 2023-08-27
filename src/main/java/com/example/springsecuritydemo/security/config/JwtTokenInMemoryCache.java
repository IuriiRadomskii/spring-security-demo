package com.example.springsecuritydemo.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class JwtTokenInMemoryCache implements JwtTokenCache {

    private final Map<String, Set<GrantedAuthority>> cache = new HashMap<>();

    @Override
    public Optional<Set<GrantedAuthority>> getAuthoritiesByJti(String jti) {
        var retval = this.cache.get(jti);
        log.info("getAuthoritiesByJti: {}, {}", jti, retval);
        return Optional.ofNullable(retval);
    }

    @Override
    public void putAuthoritiesByJti(String jti, Set<GrantedAuthority> authorities) {
        log.info("putAuthoritiesByJwtToken: {}, {}", jti, authorities);
        cache.put(jti, authorities);
    }
}
