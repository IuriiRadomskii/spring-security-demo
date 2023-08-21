package com.example.springsecuritydemo.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public class JwtDto {

    @Getter
    private String token;

}
