package com.example.springsecuritydemo.security.service;


import com.example.springsecuritydemo.security.exception.TokenCacheIsDownException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;
import java.util.Set;

public interface JwtTokenCache {

    void putAuthoritiesByLogin(String login, Set<GrantedAuthority> authorities);

    void putJtiByLogin(String login, String jti);

    Optional<Set<GrantedAuthority>> getAuthoritiesByLogin(String login) throws TokenCacheIsDownException;

    Optional<String> getJtiByLogin(String login) throws TokenCacheIsDownException;

    void removeSecurityCacheByLogin(String login);
}
