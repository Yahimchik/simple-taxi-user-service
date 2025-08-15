package com.taxisimpledrive.taxiuserservice.service;

public interface EmailService {
    void sendRegistrationConfirmationEmail(String recipient, String token);

    void sendResetPasswordEmail(String recipient, String token);
}
