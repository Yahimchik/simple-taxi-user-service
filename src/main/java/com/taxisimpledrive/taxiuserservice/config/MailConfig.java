package com.taxisimpledrive.taxiuserservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.protocol}")
    private String protocol;
    @Value("${spring.mail.debug:false}")
    private String debug;

    @Bean
    public JavaMailSender getEmailService(){
        JavaMailSenderImpl emailService = new JavaMailSenderImpl();

        emailService.setHost(host);
        emailService.setPort(port);
        emailService.setUsername(username);
        emailService.setPassword(password);

        Properties properties = emailService.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug);

        return emailService;
    }
}