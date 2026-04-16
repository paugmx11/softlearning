package com.example.softlearning.sharedkernel.domainservices.validations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.softlearning.sharedkernel.model.exceptions.ValidationException;

class CheckTest {

    @Test
    void shouldAcceptNonEmptyString() {
        assertDoesNotThrow(() -> Check.checkNotEmpty("valor", "Campo"));
    }

    @Test
    void shouldThrowExactErrorWhenStringIsEmpty() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> Check.checkNotEmpty(" ", "Campo")
        );

        assertEquals("Campo no puede estar vacío", exception.getMessage());
    }

    @Test
    void shouldAcceptPositiveInt() {
        assertDoesNotThrow(() -> Check.checkPositive(5, "Cantidad"));
    }

    @Test
    void shouldThrowExactErrorWhenIntIsNotPositive() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> Check.checkPositive(0, "Cantidad")
        );

        assertEquals("Cantidad debe ser mayor que 0", exception.getMessage());
    }

    @Test
    void shouldAcceptPositiveDouble() {
        assertDoesNotThrow(() -> Check.checkPositive(10.5, "Precio"));
    }

    @Test
    void shouldThrowExactErrorWhenDoubleIsNotPositive() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> Check.checkPositive(-2.5, "Precio")
        );

        assertEquals("Precio debe ser mayor que 0", exception.getMessage());
    }
}
