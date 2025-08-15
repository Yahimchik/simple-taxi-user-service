package com.taxisimpledrive.taxiuserservice.service.impl;

import com.taxisimpledrive.taxiuserservice.dto.*;
import com.taxisimpledrive.taxiuserservice.dto.password.CreatePasswordDTO;
import com.taxisimpledrive.taxiuserservice.dto.password.ForgotPasswordDTO;
import com.taxisimpledrive.taxiuserservice.dto.password.UpdatePasswordDTO;
import com.taxisimpledrive.taxiuserservice.mapper.UserMapper;
import com.taxisimpledrive.taxiuserservice.model.Role;
import com.taxisimpledrive.taxiuserservice.model.User;
import com.taxisimpledrive.taxiuserservice.model.VerificationToken;
import com.taxisimpledrive.taxiuserservice.repository.RoleRepository;
import com.taxisimpledrive.taxiuserservice.repository.UserRepository;
import com.taxisimpledrive.taxiuserservice.service.EmailService;
import com.taxisimpledrive.taxiuserservice.service.UserService;
import com.taxisimpledrive.taxiuserservice.service.VerificationService;
import com.taxisimpledrive.taxiuserservice.util.generator.UUIDGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

import static com.taxisimpledrive.taxiuserservice.model.enums.UserStatus.*;
import static com.taxisimpledrive.taxiuserservice.util.EntityValidationUtil.checkDuplicate;
import static com.taxisimpledrive.taxiuserservice.util.EntityValidationUtil.findByFieldOrThrow;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Class<User> USER_CLASS = User.class;
    private static final Class<Role> ROLE_CLASS = Role.class;
    private static final String PHONE_FIELD = "phoneNumber";
    private static final String DEFAULT_ROLE_USER = "USER";
    private static final String EMAIL_FIELD = "email";
    private static final String NAME = "name";

    private final UserRepository userRepository;
    private final UUIDGenerator uuidGenerator;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final VerificationService verificationService;

    @Override
    @Transactional
    public RegisterResponseDTO register(RegisterRequestDTO register) {
        checkDuplicates(register);
        User user = populateUser(register);
        user = userRepository.save(user);
        VerificationToken verificationToken = verificationService.saveToken(user);
        emailService.sendRegistrationConfirmationEmail(user.getEmail(), verificationToken.getToken());
        return userMapper.convertToResponse(user);
    }

    @Override
    @Transactional
    public void verifyUserAccount(String token) {
        verificationService.validateVerificationToken(token);
        User user = verificationService.findUserByVerificationToken(token);
        user.setStatus(ACTIVE);
        userRepository.save(user);
        verificationService.removeToken(token);
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordDTO forgot) {
        User user = getUserByEmail(forgot.email());
        user.setStatus(RESET_PASSWORD);
        VerificationToken token = verificationService.saveToken(user);
        emailService.sendResetPasswordEmail(user.getEmail(), token.getToken());
    }

    @Override
    @Transactional
    public void createPasswordResetToken(CreatePasswordDTO userCreatePasswordDto, String token) {
        verificationService.validateVerificationToken(token);
        User user = verificationService.findUserByVerificationToken(token);
        user.setPassword(passwordEncoder.encode(userCreatePasswordDto.password()));
        user.setStatus(ACTIVE);
        userRepository.save(user);
        verificationService.removeToken(token);
    }

    @Override
    @Transactional
    public void updatePassword(UpdatePasswordDTO userUpdatePasswordDto) {

    }

    private void checkDuplicates(RegisterRequestDTO register) {
        checkDuplicate(USER_CLASS, EMAIL_FIELD, register.email(), userRepository::findByEmail);
        checkDuplicate(USER_CLASS, PHONE_FIELD, register.phoneNumber(), userRepository::findByPhoneNumber);
    }

    private User populateUser(RegisterRequestDTO register) {
        Role userRole = findByFieldOrThrow(DEFAULT_ROLE_USER, ROLE_CLASS, NAME, roleRepository::findByName);
        LocalDateTime now = LocalDateTime.now();
        return User.builder()
                .id(uuidGenerator.generate())
                .phoneNumber(register.phoneNumber())
                .email(register.email())
                .password(passwordEncoder.encode(register.password()))
                .roles(Set.of(userRole))
                .createdAt(now)
                .lastVisit(now)
                .status(WAITING_ACTIVATION)
                .build();
    }

    private boolean isCurrentPasswordMatches(String matchingPassword, String currentPassword) {
        return passwordEncoder.matches(matchingPassword, currentPassword);
    }

    private User getUserByEmail(String email) {
        return findByFieldOrThrow(email, USER_CLASS, EMAIL_FIELD, userRepository::findByEmail);
    }
}