package com.example.softlearning.sharedkernel.domainservices.validations;

import com.example.softlearning.sharedkernel.model.exceptions.ValidationException;

public class Check {

    private Check() {
    }

    public static void checkNotEmpty(String value, String fieldName)
            throws ValidationException {

        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " no puede estar vacío");
        }
    }

    public static void checkPositive(double value, String fieldName)
            throws ValidationException {

        if (value <= 0) {
            throw new ValidationException(fieldName + " debe ser mayor que 0");
        }
    }

    public static void checkPositive(int value, String fieldName)
            throws ValidationException {

        if (value <= 0) {
            throw new ValidationException(fieldName + " debe ser mayor que 0");
        }
    }
}
