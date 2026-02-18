package com.example.softlearning.applicationcore.entity.order.dtos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderDTO {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "client_id")
    private int clientId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "total_amount")
    private double totalAmount;

    protected OrderDTO() {
    }

    public OrderDTO(int id, int clientId, LocalDateTime orderDate, LocalDateTime deliveryDate,
                    String status, String description, double totalAmount) {
        this.id = id;
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.description = description;
        this.totalAmount = totalAmount;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OrderDTO [id=" + id +
                ", clientId=" + clientId +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", status=" + status +
                ", description=" + description +
                ", totalAmount=" + totalAmount +
                "]";
    }
}
