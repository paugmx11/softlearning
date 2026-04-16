package com.example.softlearning.applicationcore.entity.order.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.example.softlearning.sharedkernel.model.exceptions.BuildException;

class OrderTest {

    private static final LocalDateTime ORDER_DATE = LocalDateTime.of(2026, 3, 23, 10, 0);
    private static final LocalDateTime FUTURE_ORDER_DATE = LocalDateTime.of(2099, 1, 1, 10, 0);

    @Test
    void shouldCreateOrderWithInitialStatusCreated() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");

        assertEquals(1, order.getId());
        assertEquals(10, order.getClientId());
        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertEquals(0.0, order.getTotalAmount());
        assertEquals("Pedido de prueba", order.getDescription());
    }

    @Test
    void shouldTrimDescriptionWhenCreatingOrder() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "  Pedido de prueba  ");

        assertEquals("Pedido de prueba", order.getDescription());
    }

    @Test
    void shouldAddDetailAndRecalculateTotal() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");

        order.addDetail("BK-01", "Libro Java", 20.0, 2, 10.0);

        assertEquals(1, order.getDetails().size());
        assertEquals(36.0, order.getTotalAmount());
    }

    @Test
    void shouldRecalculateTotalWhenRemovingExistingDetail() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");
        order.addDetail("BK-01", "Libro Java", 20.0, 2, 0.0);
        order.addDetail("BK-02", "Spring", 10.0, 1, 0.0);

        order.removeDetail(1);

        assertEquals(1, order.getDetails().size());
        assertEquals(10.0, order.getTotalAmount());
    }

    @Test
    void shouldConfirmOrderWhenItHasDetails() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");
        order.addDetail("BK-01", "Libro Java", 20.0, 2, 0.0);

        order.confirm();

        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
    }

    @Test
    void shouldSetDeliveryDateWhenDelivered() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");
        order.addDetail("BK-01", "Libro Java", 20.0, 2, 0.0);
        order.confirm();
        order.markAsForthcoming();

        order.deliver();

        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        assertNotNull(order.getDeliveryDate());
    }

    @Test
    void shouldReturnExactErrorWhenCreatingOrderWithInvalidData() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Order.getInstance(0, 0, null, " ")
        );

        assertEquals(
                "No se ha podido crear el pedido. El identificador del pedido debe ser mayor que 0. "
                        + "El identificador del cliente debe ser mayor que 0. "
                        + "La fecha del pedido es obligatoria. "
                        + "La descripcion del pedido es obligatoria. ",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenCreatingOrderWithFutureDate() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Order.getInstance(1, 10, FUTURE_ORDER_DATE, "Pedido de prueba")
        );

        assertEquals(
                "No se ha podido crear el pedido. La fecha del pedido no puede ser futura. ",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenCreatingOrderWithoutDescription() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Order.getInstance(1, 10, ORDER_DATE, " ")
        );

        assertEquals(
                "No se ha podido crear el pedido. La descripcion del pedido es obligatoria. ",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenCreatingOrderWithInvalidClientId() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Order.getInstance(1, 0, ORDER_DATE, "Pedido de prueba")
        );

        assertEquals(
                "No se ha podido crear el pedido. El identificador del cliente debe ser mayor que 0. ",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenConfirmingOrderWithoutDetails() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");

        BuildException exception = assertThrows(BuildException.class, order::confirm);

        assertEquals("No se puede confirmar un pedido sin productos.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenAddingDetailToConfirmedOrder() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");
        order.addDetail("BK-01", "Libro Java", 20.0, 2, 0.0);
        order.confirm();

        BuildException exception = assertThrows(
                BuildException.class,
                () -> order.addDetail("BK-02", "Spring", 30.0, 1, 0.0)
        );

        assertEquals(
                "No se pueden modificar los productos de un pedido que ya no esta en estado creado.",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenRemovingDetailFromConfirmedOrder() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");
        order.addDetail("BK-01", "Libro Java", 20.0, 2, 0.0);
        order.confirm();

        BuildException exception = assertThrows(BuildException.class, () -> order.removeDetail(1));

        assertEquals(
                "No se pueden modificar los productos de un pedido que ya no esta en estado creado.",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenRemovingDetailWithInvalidId() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");
        order.addDetail("BK-01", "Libro Java", 20.0, 2, 0.0);

        BuildException exception = assertThrows(BuildException.class, () -> order.removeDetail(0));

        assertEquals("El identificador del producto del pedido no es valido.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenRemovingDetailThatDoesNotExist() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");
        order.addDetail("BK-01", "Libro Java", 20.0, 2, 0.0);

        BuildException exception = assertThrows(BuildException.class, () -> order.removeDetail(99));

        assertEquals(
                "No existe ningun producto en el pedido con el identificador indicado.",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenMarkingAsForthcomingWithoutConfirmingFirst() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");
        order.addDetail("BK-01", "Libro Java", 20.0, 2, 0.0);

        BuildException exception = assertThrows(BuildException.class, order::markAsForthcoming);

        assertEquals(
                "Solo se puede marcar como en camino un pedido que este confirmado.",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenDeliveringWithoutBeingForthcoming() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");
        order.addDetail("BK-01", "Libro Java", 20.0, 2, 0.0);
        order.confirm();

        BuildException exception = assertThrows(BuildException.class, order::deliver);

        assertEquals("Solo se puede entregar un pedido que este en camino.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenConfirmingCancelledOrder() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");
        order.addDetail("BK-01", "Libro Java", 20.0, 2, 0.0);
        order.cancel();

        BuildException exception = assertThrows(BuildException.class, order::confirm);

        assertEquals("Solo se puede confirmar un pedido que este en estado creado.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenMarkingCancelledOrderAsForthcoming() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");
        order.cancel();

        BuildException exception = assertThrows(BuildException.class, order::markAsForthcoming);

        assertEquals(
                "Solo se puede marcar como en camino un pedido que este confirmado.",
                exception.getMessage()
        );
    }

    @Test
    void shouldAllowCancellingConfirmedOrder() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");
        order.addDetail("BK-01", "Libro Java", 20.0, 2, 0.0);
        order.confirm();

        order.cancel();

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void shouldReturnExactErrorWhenCancellingDeliveredOrder() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");
        order.addDetail("BK-01", "Libro Java", 20.0, 2, 0.0);
        order.confirm();
        order.markAsForthcoming();
        order.deliver();

        BuildException exception = assertThrows(BuildException.class, order::cancel);

        assertEquals(
                "No se puede cancelar un pedido que ya ha sido entregado.",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenCancellingOrderThatWasAlreadyCancelled() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");

        order.cancel();

        BuildException exception = assertThrows(BuildException.class, order::cancel);

        assertEquals("El pedido ya estaba cancelado.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenAddingDetailWithInvalidDiscount() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");

        BuildException exception = assertThrows(
                BuildException.class,
                () -> order.addDetail("BK-01", "Libro Java", 20.0, 2, 150.0)
        );

        assertEquals(
                "No se ha podido anadir el producto al pedido. El descuento debe estar entre 0 y 100.",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenAddingDetailWithoutProductReference() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");

        BuildException exception = assertThrows(
                BuildException.class,
                () -> order.addDetail(" ", "Libro Java", 20.0, 2, 10.0)
        );

        assertEquals(
                "No se ha podido anadir el producto al pedido. La referencia del producto es obligatoria.",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenAddingDetailWithoutProductName() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");

        BuildException exception = assertThrows(
                BuildException.class,
                () -> order.addDetail("BK-01", " ", 20.0, 2, 10.0)
        );

        assertEquals(
                "No se ha podido anadir el producto al pedido. El nombre del producto es obligatorio.",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenAddingDetailWithZeroPrice() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");

        BuildException exception = assertThrows(
                BuildException.class,
                () -> order.addDetail("BK-01", "Libro Java", 0.0, 2, 10.0)
        );

        assertEquals(
                "No se ha podido anadir el producto al pedido. El precio unitario debe ser mayor que 0.",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenAddingDetailWithZeroAmount() throws BuildException {
        Order order = Order.getInstance(1, 10, ORDER_DATE, "Pedido de prueba");

        BuildException exception = assertThrows(
                BuildException.class,
                () -> order.addDetail("BK-01", "Libro Java", 20.0, 0, 10.0)
        );

        assertEquals(
                "No se ha podido anadir el producto al pedido. La cantidad debe ser mayor que 0.",
                exception.getMessage()
        );
    }
}
