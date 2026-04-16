package com.example.softlearning.applicationcore.entity.order.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.softlearning.sharedkernel.model.exceptions.ValidationException;

class OrderDetailTest {

    @Test
    void shouldCreateOrderDetailAndCalculateSubtotal() throws ValidationException {
        OrderDetail detail = new OrderDetail(1, "BK-01", "Libro Java", 20.0, 2, 10.0);

        assertEquals(1, detail.getId());
        assertEquals("BK-01", detail.getProductRef());
        assertEquals("Libro Java", detail.getProductName());
        assertEquals(36.0, detail.getSubtotal());
    }

    @Test
    void shouldReturnExactErrorWhenReferenceIsMissing() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> new OrderDetail(1, " ", "Libro Java", 20.0, 2, 10.0)
        );

        assertEquals("La referencia del producto es obligatoria.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenDetailIdIsInvalid() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> new OrderDetail(0, "BK-01", "Libro Java", 20.0, 2, 10.0)
        );

        assertEquals("El identificador del detalle debe ser mayor que 0.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenProductNameIsMissing() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> new OrderDetail(1, "BK-01", " ", 20.0, 2, 10.0)
        );

        assertEquals("El nombre del producto es obligatorio.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenUnitPriceIsZero() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> new OrderDetail(1, "BK-01", "Libro Java", 0.0, 2, 10.0)
        );

        assertEquals("El precio unitario debe ser mayor que 0.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenAmountIsZero() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> new OrderDetail(1, "BK-01", "Libro Java", 20.0, 0, 10.0)
        );

        assertEquals("La cantidad debe ser mayor que 0.", exception.getMessage());
    }

    @Test
    void shouldAcceptDiscountAtLowerBoundary() throws ValidationException {
        OrderDetail detail = new OrderDetail(1, "BK-01", "Libro Java", 20.0, 2, 0.0);

        assertEquals(40.0, detail.getSubtotal());
    }

    @Test
    void shouldAcceptDiscountAtUpperBoundary() throws ValidationException {
        OrderDetail detail = new OrderDetail(1, "BK-01", "Libro Java", 20.0, 2, 100.0);

        assertEquals(0.0, detail.getSubtotal());
    }

    @Test
    void shouldReturnExactErrorWhenDiscountIsOutsideRange() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> new OrderDetail(1, "BK-01", "Libro Java", 20.0, 2, 120.0)
        );

        assertEquals("El descuento debe estar entre 0 y 100.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenDiscountIsNegative() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> new OrderDetail(1, "BK-01", "Libro Java", 20.0, 2, -1.0)
        );

        assertEquals("El descuento debe estar entre 0 y 100.", exception.getMessage());
    }
}
