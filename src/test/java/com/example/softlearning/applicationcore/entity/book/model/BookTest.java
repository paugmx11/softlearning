package com.example.softlearning.applicationcore.entity.book.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.example.softlearning.sharedkernel.model.exceptions.BuildException;

class BookTest {

    @Test
    void shouldCreateBookWithValidData() throws BuildException {
        Book book = Book.getInstance(
                1,
                "3rd Edition",
                "Java Avanzado",
                "Ana Lopez",
                "Manual de Java",
                "Programacion",
                29.99,
                8,
                true
        );

        assertEquals(1, book.getId());
        assertEquals("3rd Edition", book.getEdition());
        assertEquals("Java Avanzado", book.getTitle());
        assertEquals("Ana Lopez", book.getWriter());
        assertEquals("Manual de Java", book.getDescription());
        assertEquals("Programacion", book.getType());
        assertEquals(29.99, book.getPrice());
        assertEquals(8, book.getStock());
        assertTrue(book.isAvailable());
    }

    @Test
    void shouldReturnBookInfoWithRelevantFields() throws BuildException {
        Book book = Book.getInstance(
                1,
                "3rd Edition",
                "Java Avanzado",
                "Ana Lopez",
                "Manual de Java",
                "Programacion",
                29.99,
                8,
                true
        );

        assertEquals(
                "Book: Java Avanzado by Ana Lopez - Stock: 8 - Available: true",
                book.getBookInfo()
        );
    }

    @Test
    void shouldSetAvailabilityToFalseWhenStockIsZero() throws BuildException {
        Book book = Book.getInstance(
                1,
                "3rd Edition",
                "Java Avanzado",
                "Ana Lopez",
                "Manual de Java",
                "Programacion",
                29.99,
                0,
                true
        );

        assertFalse(book.isAvailable());
    }

    @Test
    void shouldTrimOptionalAndRequiredFields() throws BuildException {
        Book book = Book.getInstance(
                1,
                " 3rd Edition ",
                " Java Avanzado ",
                " Ana Lopez ",
                " Manual de Java ",
                " Programacion ",
                29.99,
                8,
                true
        );

        assertEquals("3rd Edition", book.getEdition());
        assertEquals("Java Avanzado", book.getTitle());
        assertEquals("Ana Lopez", book.getWriter());
        assertEquals("Manual de Java", book.getDescription());
        assertEquals("Programacion", book.getType());
    }

    @Test
    void shouldReturnExactErrorWhenCreatingBookWithInvalidData() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Book.getInstance(0, null, "ab", " ", null, " ", 0, -1, true)
        );

        assertEquals(
                "Id no válido. El título debe tener al menos 3 caracteres. El autor es requerido. "
                        + "El tipo es requerido. El precio debe ser mayor que 0. El stock no puede ser negativo.",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenBookTitleIsNull() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Book.getInstance(1, null, null, "Ana Lopez", null, "Programacion", 20.0, 3, true)
        );

        assertEquals("El título debe tener al menos 3 caracteres.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenBookWriterIsMissing() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Book.getInstance(1, null, "Java Avanzado", " ", null, "Programacion", 20.0, 3, true)
        );

        assertEquals("El autor es requerido.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenBookTypeIsMissing() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Book.getInstance(1, null, "Java Avanzado", "Ana Lopez", null, " ", 20.0, 3, true)
        );

        assertEquals("El tipo es requerido.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenCreatingBookWithNaNPrice() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Book.getInstance(1, null, "Java Avanzado", "Ana Lopez", null, "Programacion", Double.NaN, 3, true)
        );

        assertEquals("El precio debe ser mayor que 0.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenCreatingBookWithInfinitePrice() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Book.getInstance(1, null, "Java Avanzado", "Ana Lopez", null, "Programacion", Double.POSITIVE_INFINITY, 3, true)
        );

        assertEquals("El precio debe ser mayor que 0.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenCreatingBookWithNegativeStock() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Book.getInstance(1, null, "Java Avanzado", "Ana Lopez", null, "Programacion", 20.0, -1, true)
        );

        assertEquals("El stock no puede ser negativo.", exception.getMessage());
    }
}
