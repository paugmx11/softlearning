package com.example.softlearning;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.softlearning.applicationcore.entity.book.model.Book;
import com.example.softlearning.applicationcore.entity.client.model.Client;
import com.example.softlearning.applicationcore.entity.order.model.Order;
import com.example.softlearning.infraestructure.persistence.jpa.JpaBookRepository;
import com.example.softlearning.infraestructure.persistence.jpa.JpaClientRepository;
import com.example.softlearning.infraestructure.persistence.jpa.JpaOrderRepository;


@SpringBootApplication
// @EntityScan(basePackages = "com.example.softlearning.applicationcore.entity")
public class SoftlearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftlearningApplication.class, args);
    }

    @Bean
    CommandLineRunner loadDemoData(JpaBookRepository bookRepository,
                                   JpaClientRepository clientRepository,
                                   JpaOrderRepository orderRepository) {
        return args -> {
            if (bookRepository.count() == 0) {
                Book cleanCode = Book.getInstance(
                        1,
                        "1a",
                        "Clean Code",
                        "Robert C. Martin",
                        "Libro de buenas practicas de programacion.",
                        "programming",
                        29.95,
                        12,
                        true
                );
                Book effectiveJava = Book.getInstance(
                        2,
                        "3a",
                        "Effective Java",
                        "Joshua Bloch",
                        "Libro de referencia sobre Java.",
                        "programming",
                        39.95,
                        8,
                        true
                );

                bookRepository.save(cleanCode);
                bookRepository.save(effectiveJava);
            }

            if (clientRepository.count() == 0) {
                Client client = Client.getInstance(
                        "Paula",
                        1,
                        "12/05/2000",
                        "Calle Mayor 10",
                        "600123123",
                        "1111-2222-3333-4444",
                        "1234",
                        "CLI-001",
                        true
                );
                clientRepository.save(client);
            }

            if (orderRepository.count() == 0) {
                Client client = clientRepository.findById(1)
                        .orElseThrow(() -> new IllegalStateException("Cliente de ejemplo no encontrado."));
                Book cleanCode = bookRepository.findById(1)
                        .orElseThrow(() -> new IllegalStateException("Libro Clean Code no encontrado."));
                Book effectiveJava = bookRepository.findById(2)
                        .orElseThrow(() -> new IllegalStateException("Libro Effective Java no encontrado."));

                Order order = Order.getInstance(
                        1,
                        client.getId(),
                        java.time.LocalDateTime.now().minusDays(1),
                        "Pedido de ejemplo cargado al iniciar la aplicacion"
                );
                order.addDetail(
                        cleanCode,
                        "BK-001",
                        cleanCode.getTitle(),
                        cleanCode.getPrice(),
                        1,
                        0.0
                );
                order.addDetail(
                        effectiveJava,
                        "BK-002",
                        effectiveJava.getTitle(),
                        effectiveJava.getPrice(),
                        2,
                        5.0
                );

                orderRepository.save(order);
            }
        };
    }
}
