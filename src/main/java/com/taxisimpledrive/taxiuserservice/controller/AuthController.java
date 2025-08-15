package com.taxisimpledrive.taxiuserservice.controller;

import com.taxisimpledrive.taxiuserservice.dto.*;
import com.taxisimpledrive.taxiuserservice.dto.jwt.JWTRequestDTO;
import com.taxisimpledrive.taxiuserservice.dto.jwt.JWTResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AuthController {



    @PostMapping("/login")
    public ResponseEntity<JWTResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(new JWTResponseDTO("", ""));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTResponseDTO> refresh(@Valid @RequestBody JWTRequestDTO request) {
        return ResponseEntity.ok(new JWTResponseDTO("", ""));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody JWTRequestDTO request) {
        return ResponseEntity.noContent().build();
    }
}