package com.taxisimpledrive.taxiuserservice.service;

import com.taxisimpledrive.taxiuserservice.dto.*;
import com.taxisimpledrive.taxiuserservice.dto.jwt.JWTRequestDTO;
import com.taxisimpledrive.taxiuserservice.dto.jwt.JWTResponseDTO;

public interface AuthService {

    JWTResponseDTO authenticate(AuthRequestDTO user);

    JWTResponseDTO recreateToken(JWTRequestDTO jwtRequestDto);

    void logout(String refreshToken);

    void logoutOffAllDevices(String refreshToken);
}
