package com.example.springsecuritydemo.security.service;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

@Service
public class SecretService {

    private final static String secretKey = "b90J4s5ftoyhrnOCO1D18n4V3yvTQIpZz5VCFImHCIP4AMrb";
    private final static SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;

    public Key getKey() {
        return new SecretKeySpec(secretKey.getBytes(), ALGORITHM.getJcaName());
    }

    private String generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[36];
        random.nextBytes(bytes);
        var encoder = java.util.Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }

}
