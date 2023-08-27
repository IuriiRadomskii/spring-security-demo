package com.example.springsecuritydemo.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecretService secretService;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setIssuer("spring security demo")
                .setId(generateTokenId())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(secretService.getKey(), io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretService.getKey()).build().parseClaimsJws(token).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    public String extractJti(String token) {
        return extractClaim(token, Claims::getId);
    }

    public String extractLogin(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Boolean isTokenValid(String token) {
        var builder = Jwts.parserBuilder().setSigningKey(secretService.getKey()).build();
        return builder.isSigned(token) && isTokenNonExpired(token);
    }

    private boolean isTokenNonExpired(String token) {
        return !extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private String generateTokenId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
