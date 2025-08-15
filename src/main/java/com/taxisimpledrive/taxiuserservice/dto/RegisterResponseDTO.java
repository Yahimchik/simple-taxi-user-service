package com.taxisimpledrive.taxiuserservice.dto;

import com.taxisimpledrive.taxiuserservice.model.Role;
import com.taxisimpledrive.taxiuserservice.model.enums.UserStatus;

import java.util.Set;
import java.util.UUID;

public record RegisterResponseDTO(
        UUID id,
        String email,
        String phoneNumber,
        UserStatus status,
        Set<Role> roles
) {
}