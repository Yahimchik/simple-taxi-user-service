package com.taxisimpledrive.taxiuserservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ErrorMessages {

    public static final String ENTITY_NOT_FOUND = "%s not found with %s: %s";
    public static final String ENTITY_ALREADY_EXISTS = "%s already exists with %s: %s";
    public static final String WRONG_CREDENTIALS = "Wrong credentials provided";

    public static final String ROLE_NOT_FOUND = "Role not found with name: %s";
}