package com.example.springsecuritydemo.security.config;

import com.example.springsecuritydemo.security.exception.TokenCacheIsDownException;
import com.example.springsecuritydemo.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    public static final String AUTH_HEADER = "Authorization";
    public static final String AUTH_HEADER_PREFIX = "Bearer ";
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final JwtTokenCache cache;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTH_HEADER);
        final String jwt;
        final String login;
        final String jti;
        if (!StringUtils.hasText(authHeader)) {
            throw new BadCredentialsException("No auth header");
        }
        if (!authHeader.startsWith(AUTH_HEADER_PREFIX)) {
            throw new BadCredentialsException("Auth header doesn't start with " + AUTH_HEADER_PREFIX);
        }
        jwt = authHeader.substring(7);
        if (!jwtService.isTokenValid(jwt)) {
            throw new BadCredentialsException("Token is not valid");
        }
        try {
            login = jwtService.extractLogin(jwt);
            jti = jwtService.extractJti(jwt);
        } catch (Exception e) {
            throw new BadCredentialsException("Exception while extracting login and jti", e);
        }
        if (login.isEmpty() || jti.isEmpty()) {
            throw new BadCredentialsException("Login or jti is empty");
        }
        Optional<Set<GrantedAuthority>> authorities;
        try {
            authorities = cache.getAuthoritiesByJti(jti);
            if (authorities.isEmpty()) {
                log.info("No authorities in cache");
                throw new BadCredentialsException("No authorities in cache");
            } else {
                authorize(login, authorities.get());
            }
        } catch (TokenCacheIsDownException e1) {
            log.error("Token cache is down", e1);
            UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(login);
            } catch (UsernameNotFoundException e2) {
                throw new UsernameNotFoundException("User not found", e2);
            }
            authorize(login, new HashSet<>(userDetails.getAuthorities()));
        }
        filterChain.doFilter(request, response);
    }

    private void authorize(String login, Set<GrantedAuthority> authorities) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticated(login, null, authorities));
        SecurityContextHolder.setContext(context);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().startsWith("/api/v1/auth/signup") ||
                request.getServletPath().startsWith("/api/v1/auth/signin");
    }
}
