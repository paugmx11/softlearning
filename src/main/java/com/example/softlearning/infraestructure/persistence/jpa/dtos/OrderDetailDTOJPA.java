package com.example.softlearning.infraestructure.persistence.jpa.dtos;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "orderDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderDetailDTOJPA {
    private int id;
    private Integer bookId;
    private String productRef;
    private String productName;
    private double unitPrice;
    private int amount;
    private double discount;
    private double subtotal;

    public OrderDetailDTOJPA() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }
    public String getProductRef() { return productRef; }
    public void setProductRef(String productRef) { this.productRef = productRef; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
