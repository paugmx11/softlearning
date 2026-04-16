package com.example.softlearning.applicationcore.entity.order.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "order")
public class OrderDTO {

    private int id;

    private int clientId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime deliveryDate;

    private String status;

    private String description;

    private double totalAmount;

    @JsonManagedReference
    @JacksonXmlElementWrapper(localName = "details")
    @JacksonXmlProperty(localName = "detail")
    private List<OrderDetailDTO> details = new ArrayList<>();

    protected OrderDTO() {
    }

    public OrderDTO(int id, int clientId, LocalDateTime orderDate, LocalDateTime deliveryDate,
                    String status, String description, double totalAmount) {
        this(id, clientId, orderDate, deliveryDate, status, description, totalAmount, null);
    }

    public OrderDTO(int id, int clientId, LocalDateTime orderDate, LocalDateTime deliveryDate,
                    String status, String description, double totalAmount, List<OrderDetailDTO> details) {
        this.id = id;
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.description = description;
        this.totalAmount = totalAmount;
        this.setDetails(details);
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

    public List<OrderDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetailDTO> details) {
        List<OrderDetailDTO> safeDetails = details == null ? null : new ArrayList<>(details);
        this.details.clear();
        if (safeDetails == null) {
            return;
        }

        for (OrderDetailDTO detail : safeDetails) {
            this.addDetail(detail);
        }
    }

    public void addDetail(OrderDetailDTO detail) {
        if (detail == null) {
            return;
        }
        detail.setOrder(this);
        this.details.add(detail);
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
                ", details=" + details.size() +
                "]";
    }
}
