package com.example.softlearning.applicationcore.entity.order.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "pedido")
public class OrderSpanishDTO {

    @JacksonXmlProperty(localName = "identificador")
    private int id;

    @JacksonXmlProperty(localName = "clienteId")
    private int clientId;

    @JacksonXmlProperty(localName = "fechaPedido")
    private LocalDateTime orderDate;

    @JacksonXmlProperty(localName = "fechaEntrega")
    private LocalDateTime deliveryDate;

    @JacksonXmlProperty(localName = "estado")
    private String status;

    @JacksonXmlProperty(localName = "descripcion")
    private String description;

    @JacksonXmlProperty(localName = "importeTotal")
    private double totalAmount;

    public OrderSpanishDTO() {
    }

    @JsonGetter("identificador")
    public int getId() {
        return id;
    }

    @JsonSetter("identificador")
    public void setId(int id) {
        this.id = id;
    }

    @JsonGetter("clienteId")
    public int getClientId() {
        return clientId;
    }

    @JsonSetter("clienteId")
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @JsonGetter("fechaPedido")
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    @JsonSetter("fechaPedido")
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @JsonGetter("fechaEntrega")
    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    @JsonSetter("fechaEntrega")
    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @JsonGetter("estado")
    public String getStatus() {
        return status;
    }

    @JsonSetter("estado")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonGetter("descripcion")
    public String getDescription() {
        return description;
    }

    @JsonSetter("descripcion")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonGetter("importeTotal")
    public double getTotalAmount() {
        return totalAmount;
    }

    @JsonSetter("importeTotal")
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OrderSpanishDTO [identificador=" + id +
                ", clienteId=" + clientId +
                ", fechaPedido=" + orderDate +
                ", fechaEntrega=" + deliveryDate +
                ", estado=" + status +
                ", descripcion=" + description +
                ", importeTotal=" + totalAmount +
                "]";
    }
}
