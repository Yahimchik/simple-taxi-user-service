package com.taxisimpledrive.taxiuserservice.service.impl;

import com.taxisimpledrive.taxiuserservice.model.User;
import com.taxisimpledrive.taxiuserservice.model.VerificationToken;
import com.taxisimpledrive.taxiuserservice.repository.VerificationTokenRepository;
import com.taxisimpledrive.taxiuserservice.service.VerificationService;
import com.taxisimpledrive.taxiuserservice.service.exception.VerificationUserException;
import com.taxisimpledrive.taxiuserservice.util.generator.KeyGenerator;
import com.taxisimpledrive.taxiuserservice.util.generator.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.taxisimpledrive.taxiuserservice.model.enums.UserStatus.ACTIVE;
import static com.taxisimpledrive.taxiuserservice.util.EntityValidationUtil.findByFieldOrThrow;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private static final Class<User> USER_CLASS = User.class;
    private static final Class<VerificationToken> VERIFICATION_TOKEN_CLASS = VerificationToken.class;
    private static final String TOKEN_IS_EXPIRED_EXCEPTION = "Token is expired";
    private static final String USER_ACTIVE_EXCEPTION = "User is already active";
    private static final String TOKEN = "token";
    private static final long EXPIRATION = 4;
    private static final int KEY_LENGTH = 15;

    private final VerificationTokenRepository verificationTokenRepository;
    private final UUIDGenerator uuidGenerator;
    private final KeyGenerator keyGenerator;

    @Override
    @Transactional
    public VerificationToken saveToken(User user) {
        VerificationToken verificationToken = generateToken(user);
        return verificationTokenRepository.save(verificationToken);
    }

    @Override
    @Transactional
    public VerificationToken findByToken(String token) {
        return findByFieldOrThrow(token, VERIFICATION_TOKEN_CLASS, TOKEN, verificationTokenRepository::findByToken);
    }

    @Override
    @Transactional
    public User findUserByVerificationToken(String token) {
        return findByFieldOrThrow(token, USER_CLASS, TOKEN, verificationTokenRepository::findUserByToken);
    }

    @Override
    @Transactional
    public boolean validateVerificationToken(String token) {
        VerificationToken verificationToken = findByToken(token);
        validateTokenByExpirationDate(verificationToken);
        validateByUserStatus(verificationToken.getUser());
        return true;
    }

    @Override
    @Transactional
    public void removeToken(String token) {
        var verificationToken = findByFieldOrThrow(token, VERIFICATION_TOKEN_CLASS, TOKEN, verificationTokenRepository::findByToken);
        verificationTokenRepository.delete(verificationToken);
    }

    private VerificationToken generateToken(User user) {
        String tokenValue = keyGenerator.generateKey(KEY_LENGTH);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime accessExpiration = now.plusHours(EXPIRATION);
        return VerificationToken.builder()
                .id(uuidGenerator.generate())
                .token(tokenValue)
                .user(user)
                .expiryDate(accessExpiration)
                .build();
    }

    private void validateTokenByExpirationDate(VerificationToken verificationToken) {
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new VerificationUserException(TOKEN_IS_EXPIRED_EXCEPTION);
        }
    }

    private void validateByUserStatus(User user) {
        if (user.getStatus().equals(ACTIVE)) {
            throw new VerificationUserException(USER_ACTIVE_EXCEPTION);
        }
    }
}