package com.example.softlearning.sharedkernel.domainservices.validations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

class DataCheckTest {

    @Test
    void shouldValidateStrings() {
        assertTrue(DataCheck.isValidString("texto"));
        assertFalse(DataCheck.isValidString(" "));
        assertFalse(DataCheck.isValidString(null));
    }

    @Test
    void shouldValidateEmails() {
        assertTrue(DataCheck.isValidEmail("ana@example.com"));
        assertFalse(DataCheck.isValidEmail("ana@example"));
    }

    @Test
    void shouldValidatePositiveAndNonNegativeNumbers() {
        assertTrue(DataCheck.isPositive(1));
        assertFalse(DataCheck.isPositive(0));
        assertTrue(DataCheck.isNotNegative(0));
        assertFalse(DataCheck.isNotNegative(-1));
    }

    @Test
    void shouldValidatePriceAndIsbn() {
        assertTrue(DataCheck.isValidPrice(19.99));
        assertFalse(DataCheck.isValidPrice(0));
        assertTrue(DataCheck.isValidISBN("978-84-376-0494-7"));
        assertFalse(DataCheck.isValidISBN("123"));
    }

    @Test
    void shouldParseAndFormatDate() {
        LocalDate date = DataCheck.parseDate("23/03/2026");

        assertEquals(LocalDate.of(2026, 3, 23), date);
        assertEquals("23/03/2026", DataCheck.formatDate(date));
    }

    @Test
    void shouldReturnNullWhenParsingBlankDate() {
        assertNull(DataCheck.parseDate(" "));
    }

    @Test
    void shouldThrowWhenDateFormatIsInvalid() {
        assertThrows(DateTimeParseException.class, () -> DataCheck.parseDate("2026-03-23"));
    }
}
