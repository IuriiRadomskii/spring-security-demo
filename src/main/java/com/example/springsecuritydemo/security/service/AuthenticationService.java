package com.example.reactdemoback.security.service;

import com.example.reactdemoback.security.dto.JwtDto;
import com.example.reactdemoback.security.dto.SignInDto;
import com.example.reactdemoback.security.dto.SignUpDto;
import com.example.reactdemoback.security.exception.AlreadyExistsException;
import com.example.reactdemoback.security.model.entity.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.example.reactdemoback.security.enums.UserAuthorities.PERFORM_ACTION_1;
import static com.example.reactdemoback.security.enums.UserRoles.ROLE_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsManagerImpl userDetailsManager;
    private final AuthenticationManager authenticationManager;

    @Transactional(rollbackFor = RuntimeException.class)
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
        try {
            userDetailsManager.createUser(appUser);
        } catch (RuntimeException e) {
            log.warn("Exception while saving user " + request.getLogin(), e);
            throw new AlreadyExistsException("User already exists");
        }
    }

    public JwtDto signIn(SignInDto request) {
        if (userDetailsManager.userExists(request.getLogin())) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPass())
            );
            var user = userDetailsManager.loadUserByUsername(request.getLogin());
            var jwt = jwtService.generateToken(user);
            return new JwtDto(jwt);
        } else {
            log.warn("user does not exist");
            throw new RuntimeException("User does not exist");
        }
    }
}
