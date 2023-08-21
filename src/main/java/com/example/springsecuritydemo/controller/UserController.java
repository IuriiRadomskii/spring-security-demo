package com.example.springsecuritydemo.controller;

import com.example.springsecuritydemo.dto.UserProfileDto;
import com.example.springsecuritydemo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/profile")
    public ResponseEntity<?> addUserInfo(@RequestBody UserProfileDto userProfileDto) {
        userService.addUserInfo(userProfileDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/profile")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserProfileDto userProfileDto) {
        userService.updateUserInfo(userProfileDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getUserInfo() {
        return ResponseEntity.ok(userService.getUserInfo());
    }

}
