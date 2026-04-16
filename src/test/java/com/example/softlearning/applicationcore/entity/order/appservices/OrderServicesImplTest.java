package com.example.softlearning.applicationcore.entity.order.appservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.softlearning.applicationcore.entity.order.model.Order;
import com.example.softlearning.infraestructure.persistence.jpa.JpaOrderRepository;
import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;

@ExtendWith(MockitoExtension.class)
class OrderServicesImplTest {

    @Mock
    private JpaOrderRepository orderRepository;

    private OrderServicesImpl orderServices;

    @BeforeEach
    void setUp() {
        orderServices = new OrderServicesImpl();
        ReflectionTestUtils.setField(orderServices, "orderRepository", orderRepository);
    }

    @Test
    void shouldSerializeOrderWithDetailsToJson() throws Exception {
        Order order = buildOrder();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        String json = orderServices.getByIdToJson(1);

        assertTrue(json.contains("\"id\":1"));
        assertTrue(json.contains("\"details\":["));
        assertTrue(json.contains("\"productRef\":\"BK-01\""));
        assertTrue(json.contains("\"subtotal\":36.0"));
    }

    @Test
    void shouldSerializeOrderWithDetailsToXml() throws Exception {
        Order order = buildOrder();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        String xml = orderServices.getByIdToXml(1);

        assertTrue(xml.contains("<id>1</id>"));
        assertTrue(xml.contains("<details>"));
        assertTrue(xml.contains("<productRef>BK-01</productRef>"));
        assertTrue(xml.contains("<subtotal>36.0</subtotal>"));
    }

    @Test
    void shouldCreateOrderAndRecalculateTotalFromJsonPayload() throws Exception {
        String payload = """
                {
                  "id": 10,
                  "clientId": 4,
                  "orderDate": "2026-03-23T10:00:00",
                  "status": "CREATED",
                  "description": "Pedido de prueba",
                  "totalAmount": 999.0,
                  "details": [
                    {
                      "id": 1,
                      "productRef": "BK-01",
                      "productName": "Libro Java",
                      "unitPrice": 20.0,
                      "amount": 2,
                      "discount": 10.0
                    }
                  ]
                }
                """;

        when(orderRepository.save(org.mockito.ArgumentMatchers.any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        String response = orderServices.addFromJson(payload);

        assertTrue(response.contains("\"totalAmount\":36.0"));
        verify(orderRepository).save(org.mockito.ArgumentMatchers.argThat(order ->
                order.getTotalAmount() == 36.0
                        && order.getDetails().size() == 1
                        && order.getDetails().get(0).getOrder() == order
        ));
    }

    @Test
    void shouldDeleteExistingOrder() throws Exception {
        Order order = buildOrder();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        orderServices.deleteById(1);

        verify(orderRepository).delete(order);
    }

    @Test
    void shouldNotDeleteMissingOrder() {
        when(orderRepository.findById(99)).thenReturn(Optional.empty());

        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> orderServices.deleteById(99)
        );

        assertEquals("Order 99 not found", exception.getMessage());
        verify(orderRepository, never()).deleteById(99);
    }

    private Order buildOrder() throws Exception {
        Order order = Order.getInstance(
                1,
                10,
                LocalDateTime.of(2026, 3, 23, 10, 0),
                "Pedido de prueba"
        );
        order.addDetail("BK-01", "Libro Java", 20.0, 2, 10.0);
        order.confirm();
        return order;
    }
}
