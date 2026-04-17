package com.example.softlearning.applicationcore.entity.order.model;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.softlearning.applicationcore.entity.book.model.Book;
import com.example.softlearning.applicationcore.entity.client.model.Client;
import com.example.softlearning.sharedkernel.model.exceptions.BuildException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    private static final String CREATE_ORDER_ERROR_PREFIX = "No se ha podido crear el pedido. ";
    private static final String ADD_DETAIL_ERROR_PREFIX = "No se ha podido anadir el producto al pedido. ";

    @Id
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String description;
    private double totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<OrderDetail> details = new ArrayList<>();

    private int detailCounter = 1;

    protected Order() {
    }

    public static Order getInstance(int id,
                                    int clientId,
                                    LocalDateTime orderDate,
                                    String description) throws BuildException {

        validateOrderData(id, clientId, orderDate, description);

        Order order = new Order();
        order.id = id;
        order.client = createClientReference(clientId);
        order.orderDate = orderDate;
        order.description = description.trim();
        order.status = OrderStatus.CREATED;
        order.totalAmount = 0.0;

        return order;
    }

    public void addDetail(String productRef,
                          String productName,
                          double unitPrice,
                          int amount,
                          double discount) throws BuildException {
        addDetail(null, productRef, productName, unitPrice, amount, discount);
    }

    public void addDetail(Book book,
                          String productRef,
                          String productName,
                          double unitPrice,
                          int amount,
                          double discount) throws BuildException {

        if (status != OrderStatus.CREATED) {
            throw new BuildException(
                    "No se pueden modificar los productos de un pedido que ya no esta en estado creado."
            );
        }

        try {
            OrderDetail detail = new OrderDetail(
                    detailCounter++,
                    productRef,
                    productName,
                    unitPrice,
                    amount,
                    discount,
                    this,
                    book
            );
            details.add(detail);
            recalculateTotal();
        } catch (Exception e) {
            throw new BuildException(ADD_DETAIL_ERROR_PREFIX + e.getMessage());
        }
    }

    public void removeDetail(int detailId) throws BuildException {
        if (status != OrderStatus.CREATED) {
            throw new BuildException(
                    "No se pueden modificar los productos de un pedido que ya no esta en estado creado."
            );
        }
        if (detailId <= 0) {
            throw new BuildException("El identificador del producto del pedido no es valido.");
        }

        boolean removed = details.removeIf(d -> d.getId() == detailId);
        if (!removed) {
            throw new BuildException("No existe ningun producto en el pedido con el identificador indicado.");
        }

        recalculateTotal();
    }

    public void confirm() throws BuildException {
        if (details.isEmpty()) {
            throw new BuildException("No se puede confirmar un pedido sin productos.");
        }
        if (status != OrderStatus.CREATED) {
            throw new BuildException("Solo se puede confirmar un pedido que este en estado creado.");
        }
        status = OrderStatus.CONFIRMED;
    }

    public void markAsForthcoming() throws BuildException {
        if (status != OrderStatus.CONFIRMED) {
            throw new BuildException("Solo se puede marcar como en camino un pedido que este confirmado.");
        }
        status = OrderStatus.FORTHCOMING;
    }

    public void deliver() throws BuildException {
        if (status != OrderStatus.FORTHCOMING) {
            throw new BuildException("Solo se puede entregar un pedido que este en camino.");
        }
        status = OrderStatus.DELIVERED;
        deliveryDate = LocalDateTime.now();
    }

    public void cancel() throws BuildException {
        if (status == OrderStatus.CANCELLED) {
            throw new BuildException("El pedido ya estaba cancelado.");
        }
        if (status == OrderStatus.DELIVERED) {
            throw new BuildException("No se puede cancelar un pedido que ya ha sido entregado.");
        }
        if (status == OrderStatus.FINISHED) {
            throw new BuildException("No se puede cancelar un pedido que ya ha sido finalizado.");
        }
        status = OrderStatus.CANCELLED;
    }

    private static void validateOrderData(int id,
                                          int clientId,
                                          LocalDateTime orderDate,
                                          String description) throws BuildException {
        StringBuilder errorMessage = new StringBuilder();

        if (id <= 0) {
            errorMessage.append("El identificador del pedido debe ser mayor que 0. ");
        }
        if (clientId <= 0) {
            errorMessage.append("El identificador del cliente debe ser mayor que 0. ");
        }
        if (orderDate == null) {
            errorMessage.append("La fecha del pedido es obligatoria. ");
        } else if (orderDate.isAfter(LocalDateTime.now())) {
            errorMessage.append("La fecha del pedido no puede ser futura. ");
        }
        if (description == null || description.trim().isEmpty()) {
            errorMessage.append("La descripcion del pedido es obligatoria. ");
        }

        if (errorMessage.length() > 0) {
            throw new BuildException(CREATE_ORDER_ERROR_PREFIX + errorMessage);
        }
    }

    private void recalculateTotal() {
        totalAmount = 0.0;
        for (OrderDetail d : details) {
            totalAmount += d.getSubtotal();
        }
    }

    public int getId() { return id; }
    public int getClientId() { return client != null && client.getId() != null ? client.getId() : 0; }
    public Client getClient() { return client; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public LocalDateTime getDeliveryDate() { return deliveryDate; }
    public OrderStatus getStatus() { return status; }
    public double getTotalAmount() { return totalAmount; }
    public List<OrderDetail> getDetails() { return List.copyOf(details); }
    public String getDescription() { return description; }

    public String getOrderDetails() {
        return "Pedido #" + id +
                " | Cliente: " + getClientId() +
                " | Estado: " + status +
                " | Total: " + totalAmount + " EUR";
    }

    private static Client createClientReference(int clientId) {
        try {
            Constructor<Client> constructor = Client.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Client client = constructor.newInstance();
            client.setId(clientId);
            return client;
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo crear la referencia al cliente.", e);
        }
    }
}
