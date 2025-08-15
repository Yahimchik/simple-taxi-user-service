package com.taxisimpledrive.taxiuserservice.dto;

import com.taxisimpledrive.taxiuserservice.security.validation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @Email(message = "Email must be valid")
        @NotBlank(message = "Email is required")
        @Schema(
                description = "User email, used for login",
                example = "user@example.com"
        )
        String email,

        @Pattern(
                regexp = "\\+?[0-9]{10,15}",
                message = "Phone number must contain between 10 and 15 digits and may start with +"
        )
        @Schema(
                description = "User phone number, optional, may start with +",
                example = "+12345678901",
                minLength = 10,
                maxLength = 15
        )
        String phoneNumber,

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
        String password
) {
}
