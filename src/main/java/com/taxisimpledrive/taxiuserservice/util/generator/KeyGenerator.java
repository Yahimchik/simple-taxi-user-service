package com.taxisimpledrive.taxiuserservice.util.generator;

import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

@Component
public class KeyGenerator {
    public String generateKey(int keyLength) {
        BytesKeyGenerator tokenGenerator = KeyGenerators.secureRandom(keyLength);
        return new String(Base64.encodeBase64URLSafe(tokenGenerator.generateKey()), StandardCharsets.US_ASCII);
    }
}