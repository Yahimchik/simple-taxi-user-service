package com.taxisimpledrive.taxiuserservice.service.impl;

import com.taxisimpledrive.taxiuserservice.dto.*;
import com.taxisimpledrive.taxiuserservice.dto.jwt.JWTRequestDTO;
import com.taxisimpledrive.taxiuserservice.dto.jwt.JWTResponseDTO;
import com.taxisimpledrive.taxiuserservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public JWTResponseDTO authenticate(AuthRequestDTO user) {
        return null;
    }

    @Override
    public JWTResponseDTO recreateToken(JWTRequestDTO jwtRequestDto) {
        return null;
    }

    @Override
    public void logout(String refreshToken) {

    }

    @Override
    public void logoutOffAllDevices(String refreshToken) {

    }
}