package com.taxisimpledrive.taxiuserservice.service.impl;

import com.taxisimpledrive.taxiuserservice.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final String CONFIRMATION_REGISTRATION_URL = "http://%s/api/v1/user/verify?token=%s";
    private static final String CONFIRMATION_PASSWORD_UPDATING_URL = "http://%s/api/v1/user/password/forgot?token=%s";

    @Value("${spring.mail.username}")
    private String sender;
    @Value("${hostname}")
    private String hostname;

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendRegistrationConfirmationEmail(String recipient, String token) {
        try {
            String confirmationUrl = String.format(CONFIRMATION_REGISTRATION_URL, hostname, token);

            Context context = new Context();
            context.setVariable("username", recipient);
            context.setVariable("confirmationUrl", confirmationUrl);

            String htmlContent = templateEngine.process("registration_email", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setFrom(sender);
            helper.setTo(recipient);
            helper.setSubject("Confirmation of registration");
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }


    @Override
    public void sendResetPasswordEmail(String recipient, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(recipient);
        message.setSubject("Change password");
        message.setText(String.format(CONFIRMATION_PASSWORD_UPDATING_URL, hostname, token));

        mailSender.send(message);
    }
}