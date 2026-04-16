package com.example.softlearning.applicationcore.entity.order.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.softlearning.applicationcore.entity.order.dtos.OrderDTO;
import com.example.softlearning.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.sharedkernel.model.exceptions.ValidationException;
import com.example.softlearning.support.TestDataFactory;

class OrderMapperTest {

    @Test
    void shouldValidateOrderDtoAndRecalculateTotal() throws BuildException, ValidationException {
        OrderDTO order = TestDataFactory.validOrderDTO();
        order.setTotalAmount(999.0);

        assertDoesNotThrow(() -> OrderMapper.orderFromDTO(order));
        assertEquals(36.0, order.getTotalAmount());
        assertEquals(order, order.getDetails().get(0).getOrder());
    }

    @Test
    void shouldReturnExactErrorWhenOrderDtoHasFutureDate() {
        OrderDTO order = TestDataFactory.validOrderDTO();
        order.setOrderDate(java.time.LocalDateTime.of(2099, 1, 1, 10, 0));

        BuildException exception = assertThrows(
                BuildException.class,
                () -> OrderMapper.orderFromDTO(order)
        );

        assertEquals("La fecha del pedido no puede ser futura.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenOrderDetailIsInvalid() {
        OrderDTO order = TestDataFactory.validOrderDTO();
        order.getDetails().get(0).setAmount(0);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> OrderMapper.orderFromDTO(order)
        );

        assertEquals("La cantidad debe ser mayor que 0.", exception.getMessage());
    }

    @Test
    void shouldReturnNullWhenCopyingNullOrderDto() {
        assertNull(OrderMapper.copy(null));
    }

    @Test
    void shouldCopyOrderDtoWithoutSharingReference() {
        OrderDTO source = TestDataFactory.validOrderDTO();

        OrderDTO copy = OrderMapper.copy(source);

        assertNotSame(source, copy);
        assertEquals(source.getId(), copy.getId());
        assertEquals(source.getClientId(), copy.getClientId());
        assertEquals(source.getOrderDate(), copy.getOrderDate());
        assertEquals(source.getDeliveryDate(), copy.getDeliveryDate());
        assertEquals(source.getStatus(), copy.getStatus());
        assertEquals(source.getDescription(), copy.getDescription());
        assertEquals(source.getTotalAmount(), copy.getTotalAmount());
        assertEquals(1, copy.getDetails().size());
        assertNotSame(source.getDetails().get(0), copy.getDetails().get(0));
        assertEquals(source.getDetails().get(0).getProductRef(), copy.getDetails().get(0).getProductRef());
    }
}
