package com.taxisimpledrive.taxiuserservice.security.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
    String message() default "Password must have at least 8 symbols, 1 upper-case symbol and 1 digit";
    Class[] groups() default {};
    Class[] payload() default {};
}
