package com.taxisimpledrive.taxiuserservice.dto.password;

import com.taxisimpledrive.taxiuserservice.security.validation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDTO(
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
        @Schema(
                description = "User password. Minimum 8 characters, maximum 50",
                example = "S3cureP@ssw0rd",
                minLength = 8,
                maxLength = 50,
                format = "password"
        )
        @Password
        String passwordBefore,
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
        @Schema(
                description = "User password. Minimum 8 characters, maximum 50",
                example = "S3cureP@ssw0rd",
                minLength = 8,
                maxLength = 50,
                format = "password"
        )
        @Password
        String passwordAfter
) {
}
