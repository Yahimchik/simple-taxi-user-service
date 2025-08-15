package com.taxisimpledrive.taxiuserservice.security.jwt;

import com.taxisimpledrive.taxiuserservice.dto.RegisterResponseDTO;
import io.jsonwebtoken.Claims;

import java.time.LocalDateTime;

public interface JwtTokenProvider {
    String generateAccessToken(RegisterResponseDTO user);

    String generateRefreshToken(RegisterResponseDTO user);

    boolean validateAccessToken(String accessToken);

    boolean validateRefreshToken(String refreshToken);

    Claims getClaimsFromAccessToken(String accessToken);

    Claims getClaimsFromRefreshToken(String refreshToken);

    String getLoginFromAccessToken(String accessToken);

    String getLoginFromRefreshToken(String refreshToken);

    LocalDateTime getExpirationDateFromToken(String token, boolean isRefresh);
}
