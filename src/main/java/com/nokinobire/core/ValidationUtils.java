package com.nokinobire.core;

import java.io.File;
import java.util.Objects;

public class ValidationUtils {

    public static void validateNotNull(Object... params) {
        for (Object objectToValidate : params) {
            if (Objects.isNull(objectToValidate)) {
                throw new IllegalArgumentException("Method param is null");
            }
        }
    }

    public static void validateFileExist(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("File doesnt exist");
        }

    }

}
