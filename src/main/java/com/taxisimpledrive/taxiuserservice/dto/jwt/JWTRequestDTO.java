package com.taxisimpledrive.taxiuserservice.dto.jwt;

import jakarta.validation.constraints.NotBlank;

public record JWTRequestDTO(
        @NotBlank(message = "Refresh token обязателен")
        String refreshToken
) {}
