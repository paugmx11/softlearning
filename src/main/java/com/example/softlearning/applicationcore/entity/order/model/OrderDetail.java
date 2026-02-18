package com.example.softlearning.applicationcore.entity.order.model;

import com.example.softlearning.sharedkernel.domainservices.validations.Check;
import com.example.softlearning.sharedkernel.model.exceptions.ValidationException;

public class OrderDetail {

    private final int id;
    private final String productRef;
    private final String productName;
    private double unitPrice;
    private int amount;
    private double discount;

    OrderDetail(int id, String productRef, String productName, double unitPrice, int amount,double discount) throws ValidationException {
        Check.checkNotEmpty(productRef, "Referència de producte");
        Check.checkNotEmpty(productName, "Nom de producte");
        Check.checkPositive(unitPrice, "Preu unitari");
        Check.checkPositive(amount, "Quantitat");

        this.id = id;
        this.productRef = productRef;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.discount = discount;
    }

    public double getSubtotal() {
        double subtotal = unitPrice * amount;
        double discountAmount = subtotal * (discount / 100);
        return Math.round((subtotal - discountAmount) * 100.0) / 100.0;
    }

    // Getters
    public int getId() { return id; }
    public String getProductRef() { return productRef; }
    public String getProductName() { return productName; }
    public double getUnitPrice() { return unitPrice; }
    public int getAmount() { return amount; }
    public double getDiscount() { return discount; }

    // Setters
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public void setAmount(int amount) { this.amount = amount; }
    public void setDiscount(double discount) { this.discount = discount; }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", productRef='" + productRef + '\'' +
                ", productName='" + productName + '\'' +
                ", unitPrice=" + unitPrice +
                ", amount=" + amount +
                ", discount=" + discount + "%" +
                ", subtotal=" + getSubtotal() +
                '}';
    }
}
