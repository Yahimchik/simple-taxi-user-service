package com.taxisimpledrive.taxiuserservice.security.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)[^\\s]{8,50}$";


    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) return false;
        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
    }
}
