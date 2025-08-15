package com.taxisimpledrive.taxiuserservice.service;

import com.taxisimpledrive.taxiuserservice.model.User;
import com.taxisimpledrive.taxiuserservice.model.VerificationToken;

public interface VerificationService {
    VerificationToken saveToken(User user);

    VerificationToken findByToken(String token);

    User findUserByVerificationToken(String token);

    boolean validateVerificationToken(String token);

    void removeToken(String token);
}
