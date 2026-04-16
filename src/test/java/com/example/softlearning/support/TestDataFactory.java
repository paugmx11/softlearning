package com.example.softlearning.support;

import java.time.LocalDateTime;
import java.util.List;

import com.example.softlearning.applicationcore.entity.book.dtos.BookDTO;
import com.example.softlearning.applicationcore.entity.client.dtos.ClientDTO;
import com.example.softlearning.applicationcore.entity.order.dtos.OrderDTO;
import com.example.softlearning.applicationcore.entity.order.dtos.OrderDetailDTO;

public final class TestDataFactory {

    private TestDataFactory() {
    }

    public static ClientDTO validClientDTO() {
        return new ClientDTO(
                1,
                "Ana Lopez",
                "15/01/1990",
                "Calle Mayor 10",
                "600123123",
                "1234",
                "clave",
                "CLI-001",
                true
        );
    }

    public static BookDTO validBookDTO() {
        return new BookDTO(
                1,
                "3rd Edition",
                "Java Avanzado",
                "Ana Lopez",
                "Manual de Java",
                "Programacion",
                29.99,
                5,
                true
        );
    }

    public static OrderDetailDTO validOrderDetailDTO() {
        return new OrderDetailDTO(1, "BK-01", "Libro Java", 20.0, 2, 10.0);
    }

    public static OrderDTO validOrderDTO() {
        return new OrderDTO(
                1,
                10,
                LocalDateTime.of(2026, 3, 23, 10, 0),
                LocalDateTime.of(2026, 3, 24, 12, 0),
                "CONFIRMED",
                "Pedido de prueba",
                36.0,
                List.of(validOrderDetailDTO())
        );
    }
}
