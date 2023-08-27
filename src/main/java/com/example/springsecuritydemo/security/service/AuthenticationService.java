package com.example.springsecuritydemo.security.service;

import com.example.springsecuritydemo.security.dto.JwtDto;
import com.example.springsecuritydemo.security.dto.SignInDto;
import com.example.springsecuritydemo.security.dto.SignUpDto;
import com.example.springsecuritydemo.security.model.entity.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

import static com.example.springsecuritydemo.security.enums.UserAuthorities.PERFORM_ACTION_1;
import static com.example.springsecuritydemo.security.enums.UserRoles.ROLE_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsManagerImpl userDetailsManager;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenCache jwtTokenCache;

    public void signUp(SignUpDto request) {
        log.debug("signUp: {}", request);
        var pass = passwordEncoder.encode(request.getPass());
        var appUser = AppUser
            .builder()
            .login(request.getLogin())
            .pass(pass)
            .roles(Collections.singletonList(ROLE_USER))
            .authorities(Collections.singletonList(PERFORM_ACTION_1))
            .build();
        userDetailsManager.createUser(appUser);
    }

    public JwtDto signIn(SignInDto request) {
        if (userDetailsManager.userExists(request.getLogin())) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPass())
            );
            var user = userDetailsManager.loadUserByUsername(request.getLogin());
            var jwt = jwtService.generateToken(user);
            var jti = jwtService.extractJti(jwt);
            jwtTokenCache.putAuthoritiesByJti(jti, new HashSet<>(user.getAuthorities()));
            return new JwtDto(jwt);
        } else {
            log.warn("user does not exist");
            throw new RuntimeException("User does not exist");
        }
    }
}
