package com.taxisimpledrive.taxiuserservice.dto.password;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordDTO(
        @Email(message = "Email must be valid")
        @NotBlank(message = "Email is required")
        @Schema(
                description = "User email, used for login",
                example = "user@example.com"
        )
        String email
) {
}