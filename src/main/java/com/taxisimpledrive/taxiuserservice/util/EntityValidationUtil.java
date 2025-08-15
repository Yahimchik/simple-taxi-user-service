package com.taxisimpledrive.taxiuserservice.util;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.function.Function;

import static com.taxisimpledrive.taxiuserservice.util.ErrorMessages.ENTITY_ALREADY_EXISTS;
import static com.taxisimpledrive.taxiuserservice.util.ErrorMessages.ENTITY_NOT_FOUND;

@UtilityClass
public final class EntityValidationUtil {

    public static <T, E> void checkDuplicate(Class<E> entityClass, String fieldName, T value, Function<T, Optional<E>> finder) {
        if (finder.apply(value).isPresent()) {
            throw new EntityExistsException(String.format(ENTITY_ALREADY_EXISTS, entityClass.getSimpleName(), fieldName, value));
        }
    }

    public static <T, V> T findByFieldOrThrow(V value, Class<T> entityClass, String fieldName, Function<V, Optional<T>> finder) {
        return finder.apply(value)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, entityClass.getSimpleName(), fieldName, value)));
    }
}