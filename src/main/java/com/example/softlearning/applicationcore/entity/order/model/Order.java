package com.example.softlearning.applicationcore.entity.order.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.softlearning.sharedkernel.model.exceptions.BuildException;
public class Order {

    private int id;
    private int clientId;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private OrderStatus status;
    private String description;
    private double totalAmount;

    private final List<OrderDetail> details = new ArrayList<>();
    private int detailCounter = 1;

    private Order() { }

    public static Order getInstance(int id,
                                    int clientId,
                                    LocalDateTime orderDate,
                                    String description) throws BuildException {

        String errorMessage = "";

        if (id <= 0) {
            errorMessage += "El identificador del pedido debe ser mayor que 0. ";
        }
        if (clientId <= 0) {
            errorMessage += "El identificador del cliente debe ser mayor que 0. ";
        }
        if (orderDate == null) {
            errorMessage += "La fecha del pedido es obligatoria. ";
        }

        if (!errorMessage.isEmpty()) {
            throw new BuildException("Error al crear el pedido: " + errorMessage);
        }

        Order order = new Order();
        order.id = id;
        order.clientId = clientId;
        order.orderDate = orderDate;
        order.description = description;
        order.status = OrderStatus.CREATED;
        order.totalAmount = 0.0;

        return order;
    }


    public void addDetail(String productRef,
                          String productName,
                          double unitPrice,
                          int amount,
                          double discount) throws BuildException {

        if (status != OrderStatus.CREATED) {
            throw new BuildException(
                    "No se pueden añadir productos a un pedido que no esté en estado CREADO."
            );
        }

        try {
            OrderDetail detail = new OrderDetail(
                    detailCounter++,
                    productRef,
                    productName,
                    unitPrice,
                    amount,
                    discount
            );
            details.add(detail);
            recalculateTotal();
        } catch (Exception e) {
            throw new BuildException("Error al añadir el detalle del pedido: " + e.getMessage());
        }
    }

    public void removeDetail(int detailId) throws BuildException {
        if (status != OrderStatus.CREATED) {
            throw new BuildException(
                    "No se pueden eliminar productos de un pedido que no esté en estado CREADO."
            );
        }
        details.removeIf(d -> d.getId() == detailId);
        recalculateTotal();
    }

    public void confirm() throws BuildException {
        if (details.isEmpty()) {
            throw new BuildException(
                    "No se puede confirmar un pedido sin productos."
            );
        }
        if (status != OrderStatus.CREATED) {
            throw new BuildException(
                    "Solo se pueden confirmar pedidos en estado CREADO."
            );
        }
        status = OrderStatus.CONFIRMED;
    }

    public void markAsForthcoming() throws BuildException {
        if (status != OrderStatus.CONFIRMED) {
            throw new BuildException(
                    "Solo se pueden marcar como EN CAMINO los pedidos en estado CONFIRMADO."
            );
        }
        status = OrderStatus.FORTHCOMING;
    }

    public void deliver() throws BuildException {
        if (status != OrderStatus.FORTHCOMING) {
            throw new BuildException(
                    "Solo se pueden entregar los pedidos que estén EN CAMINO (FORTHCOMING)."
            );
        }
        status = OrderStatus.DELIVERED;
        deliveryDate = LocalDateTime.now();
    }

    public void cancel() throws BuildException {
        if (status == OrderStatus.DELIVERED || status == OrderStatus.FINISHED || status == OrderStatus.CANCELLED) {
            throw new BuildException(
                    "No se puede cancelar un pedido que ya ha sido entregado, finalizado o cancelado."
            );
        }
        status = OrderStatus.CANCELLED;
    }

    private void recalculateTotal() {
        totalAmount = 0.0;
        for (OrderDetail d : details) {
            totalAmount += d.getSubtotal();
        }
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public List<OrderDetail> getDetails() {
        return List.copyOf(details);
    }

    public String getDescription() {
        return description;
    }

    public String getOrderDetails() {
        return "Pedido #" + id +
                " | Cliente: " + clientId +
                " | Estado: " + status +
                " | Total: " + totalAmount + " €";
    }
}
