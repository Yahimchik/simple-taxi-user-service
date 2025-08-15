package com.taxisimpledrive.taxiuserservice.service;

import com.taxisimpledrive.taxiuserservice.dto.*;
import com.taxisimpledrive.taxiuserservice.dto.password.CreatePasswordDTO;
import com.taxisimpledrive.taxiuserservice.dto.password.ForgotPasswordDTO;
import com.taxisimpledrive.taxiuserservice.dto.password.UpdatePasswordDTO;

public interface UserService {

    RegisterResponseDTO register(RegisterRequestDTO loginRequestDTO);

    void verifyUserAccount(String token);

    void forgotPassword(ForgotPasswordDTO userForgotPasswordDto);

    void createPasswordResetToken(CreatePasswordDTO userCreatePasswordDto, String token);

    void updatePassword(UpdatePasswordDTO userUpdatePasswordDto);
}
