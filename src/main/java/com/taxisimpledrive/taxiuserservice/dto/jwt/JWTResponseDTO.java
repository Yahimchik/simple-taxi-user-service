package com.taxisimpledrive.taxiuserservice.dto.jwt;

public record JWTResponseDTO(
        String accessToken,
        String refreshToken
) {
}