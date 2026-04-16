package com.example.softlearning.infraestructure.persistence.jpa.dtos;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderDTOJPA {
    private int id;
    private int clientId;
    private String orderDate;
    private String deliveryDate;
    private String description;
    private String status;
    private double totalAmount;

    @XmlElementWrapper(name = "details")
    @XmlElement(name = "orderDetail")
    private List<OrderDetailDTOJPA> details;

    public OrderDTOJPA() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
    public String getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(String deliveryDate) { this.deliveryDate = deliveryDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public List<OrderDetailDTOJPA> getDetails() { return details; }
    public void setDetails(List<OrderDetailDTOJPA> details) { this.details = details; }
}
