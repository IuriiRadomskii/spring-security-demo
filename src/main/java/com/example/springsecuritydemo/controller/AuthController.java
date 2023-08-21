package com.example.reactdemoback.controller;

import com.example.reactdemoback.security.dto.JwtDto;
import com.example.reactdemoback.security.dto.SignInDto;
import com.example.reactdemoback.security.dto.SignUpDto;
import com.example.reactdemoback.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpDto) {
        authenticationService.signUp(signUpDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtDto> signIn(@RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(authenticationService.signIn(signInDto));
    }
}
