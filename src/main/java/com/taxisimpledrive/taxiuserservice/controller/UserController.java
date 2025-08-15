package com.taxisimpledrive.taxiuserservice.controller;

import com.taxisimpledrive.taxiuserservice.dto.RegisterRequestDTO;
import com.taxisimpledrive.taxiuserservice.dto.RegisterResponseDTO;
import com.taxisimpledrive.taxiuserservice.dto.password.CreatePasswordDTO;
import com.taxisimpledrive.taxiuserservice.dto.password.ForgotPasswordDTO;
import com.taxisimpledrive.taxiuserservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO user) {
        log.info("Registering user: {}", user);
        return ResponseEntity.ok(userService.register(user));
    }

    @GetMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> verifyUserAccount(@RequestParam(value = "token") String token) {
        userService.verifyUserAccount(token);
        String html = "<html><body><h2>Ваш аккаунт успешно подтверждён!</h2></body></html>";
        return ResponseEntity.ok().body(html);
    }

    @PostMapping("/password/forgot")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgot) {
        userService.forgotPassword(forgot);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password/recovery")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> resetPasswordByEmail(@Valid @RequestBody CreatePasswordDTO create,
                                                     @RequestParam(value = "token") String token) {
        userService.createPasswordResetToken(create, token);
        return ResponseEntity.ok().build();
    }
}