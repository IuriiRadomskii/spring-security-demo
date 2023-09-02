package com.example.springsecuritydemo.security.service;

import com.example.springsecuritydemo.security.exception.TokenCacheIsDownException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisTokenCacheService implements JwtTokenCache {

    private static final String JTI_PREFIX = "jti:";
    private static final String AUTHORITIES_PREFIX = "authorities:";

    private final StringRedisTemplate redisTemplate;

    @Override
    public void putAuthoritiesByLogin(String login, Set<GrantedAuthority> authorities) {
        log.debug("putAuthoritiesByJwtToken: {}, {}", login, authorities.toString());
        redisTemplate.opsForValue().set(AUTHORITIES_PREFIX + login, authorities.toString());
    }

    @Override
    public void putJtiByLogin(String login, String jti) {
        log.debug("putJtiByLogin: {}, {}", login, jti);
        redisTemplate.opsForValue().set(JTI_PREFIX + login, jti);
    }

    @Override
    public Optional<Set<GrantedAuthority>> getAuthoritiesByLogin(String login) throws TokenCacheIsDownException {
        var authorities = redisTemplate.opsForValue().get(AUTHORITIES_PREFIX + login);
        log.debug("getAuthoritiesByLogin: {}, {}", login, authorities);
        return Optional.ofNullable(authorities)
                .map(s -> s.substring(1, s.length() - 1))
                .map(s -> s.split(","))
                .map(strings -> {
                    var set = new HashSet<GrantedAuthority>();
                    for (var s : strings) {
                        set.add(s::trim);
                    }
                    return set;
                });
    }

    @Override
    public Optional<String> getJtiByLogin(String login) throws TokenCacheIsDownException {
        var jti = redisTemplate.opsForValue().get(JTI_PREFIX + login);
        log.debug("getJtiByLogin: {}, {}", login, jti);
        return Optional.ofNullable(jti);
    }

    @Override
    public void removeSecurityCacheByLogin(String login) {
        log.debug("removeSecurityCacheByLogin: {}", login);
        redisTemplate.delete(login);
    }
}
