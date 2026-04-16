package com.example.softlearning.applicationcore.entity.order.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "detail")
public class OrderDetailDTO {

    private int id;

    private String productRef;

    private String productName;

    private double unitPrice;

    private int amount;

    private double discount;

    @JsonBackReference
    @JsonIgnore
    private OrderDTO order;

    protected OrderDetailDTO() {
    }

    public OrderDetailDTO(int id, String productRef, String productName, double unitPrice, int amount, double discount) {
        this.id = id;
        this.productRef = productRef;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductRef() {
        return productRef;
    }

    public void setProductRef(String productRef) {
        this.productRef = productRef;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @JsonIgnore
    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    @JacksonXmlProperty(localName = "subtotal")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    public double getSubtotal() {
        double subtotal = unitPrice * amount;
        double discountAmount = subtotal * (discount / 100.0);
        return Math.round((subtotal - discountAmount) * 100.0) / 100.0;
    }
}
