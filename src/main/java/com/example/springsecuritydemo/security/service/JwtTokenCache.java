package com.example.springsecuritydemo.security.service;


import com.example.springsecuritydemo.security.exception.TokenCacheIsDownException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;
import java.util.Set;

public interface JwtTokenCache {

    Optional<Set<GrantedAuthority>> getAuthoritiesByJti(String jti) throws TokenCacheIsDownException;

    void putAuthoritiesByJti(String jti, Set<GrantedAuthority> authorities);

}
