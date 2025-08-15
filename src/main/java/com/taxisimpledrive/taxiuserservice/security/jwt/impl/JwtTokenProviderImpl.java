package com.taxisimpledrive.taxiuserservice.security.jwt.impl;

import com.taxisimpledrive.taxiuserservice.dto.RegisterResponseDTO;
import com.taxisimpledrive.taxiuserservice.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import static java.time.Duration.ofDays;
import static java.time.Duration.ofMinutes;

@Slf4j
@Component
public class JwtTokenProviderImpl implements JwtTokenProvider {

    public static final int ACCESS_TOKEN_VALIDITY_MINUTES = 30;
    public static final int REFRESH_TOKEN_VALIDITY_DAYS = 15;

    private final SecretKey jwtAccessSecretKey;
    private final SecretKey jwtRefreshSecretKey;

    public JwtTokenProviderImpl(@Value("${spring.application.security.jwt.secret.access}") String jwtAccessSecret,
                                @Value("${spring.application.security.jwt.secret.refresh}") String jwtRefreshSecret) {
        this.jwtAccessSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    @Override
    public String generateAccessToken(RegisterResponseDTO user) {
        return generateToken(user, jwtAccessSecretKey, ofMinutes(ACCESS_TOKEN_VALIDITY_MINUTES), null);
    }

    @Override
    public String generateRefreshToken(RegisterResponseDTO user) {
        return generateToken(user, jwtRefreshSecretKey, ofDays(REFRESH_TOKEN_VALIDITY_DAYS), null);
    }

    @Override
    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, jwtAccessSecretKey);
    }

    @Override
    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecretKey);
    }

    @Override
    public Claims getClaimsFromAccessToken(String accessToken) {
        return getClaimsFromToken(accessToken, jwtAccessSecretKey);
    }

    @Override
    public Claims getClaimsFromRefreshToken(String refreshToken) {
        return getClaimsFromToken(refreshToken, jwtRefreshSecretKey);
    }

    @Override
    public String getLoginFromAccessToken(String accessToken) {
        return getClaimsFromToken(accessToken, jwtAccessSecretKey).getSubject();
    }

    @Override
    public String getLoginFromRefreshToken(String refreshToken) {
        return getClaimsFromToken(refreshToken, jwtRefreshSecretKey).getSubject();
    }

    @Override
    public LocalDateTime getExpirationDateFromToken(String token, boolean isRefresh) {
        return toLocalDateTime(
                isRefresh ? getClaimsFromRefreshToken(token).getExpiration()
                        : getClaimsFromAccessToken(token).getExpiration()
        );
    }

    private String generateToken(RegisterResponseDTO user, SecretKey secretKey, Duration validity, Map<String, Object> additionalClaims) {
        Claims claims = Jwts.claims().setSubject(user.id().toString());
        claims.put("email", user.email());
        claims.put("roles", user.roles());

        if (additionalClaims != null) {
            claims.putAll(additionalClaims);
        }

        Instant expirationInstant = LocalDateTime.now()
                .plus(validity)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        Date expirationDate = Date.from(expirationInstant);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    private boolean validateToken(String token, Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
        }
        return false;
    }

    private Claims getClaimsFromToken(String token, Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}