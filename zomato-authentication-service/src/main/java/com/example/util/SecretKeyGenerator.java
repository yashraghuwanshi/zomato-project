package com.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretKeyGenerator.class);

    public static String generateSecretKey(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32];
        random.nextBytes(key);
        String secretKey = Base64.getEncoder().encodeToString(key);
        LOGGER.info("SECRET KEY: {}", secretKey);
        return secretKey;
    }
}
