package com.example.softlearning.applicationcore.entity.order.model;

import com.example.softlearning.sharedkernel.model.exceptions.ValidationException;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Relación Many-to-One necesaria para que JPA gestione la clave ajena
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String productRef;
    private String productName;
    private double unitPrice;
    private int amount;
    private double discount;

    // Constructor vacío requerido por JPA
    protected OrderDetail() { 
        this.id = 0;
        this.productRef = null;
        this.productName = null;
    }

    OrderDetail(int id,
                String productRef,
                String productName,
                double unitPrice,
                int amount,
                double discount) throws ValidationException {
        this(id, productRef, productName, unitPrice, amount, discount, null);
    }

    // Constructor modificado para incluir la referencia al padre (Order)
    OrderDetail(int id,
                String productRef,
                String productName,
                double unitPrice,
                int amount,
                double discount,
                Order order) throws ValidationException {
        
        if (id <= 0) {
            throw new ValidationException("El identificador del detalle debe ser mayor que 0.");
        }
        if (productRef == null || productRef.trim().isEmpty()) {
            throw new ValidationException("La referencia del producto es obligatoria.");
        }
        if (productName == null || productName.trim().isEmpty()) {
            throw new ValidationException("El nombre del producto es obligatorio.");
        }
        if (unitPrice <= 0) {
            throw new ValidationException("El precio unitario debe ser mayor que 0.");
        }
        if (amount <= 0) {
            throw new ValidationException("La cantidad debe ser mayor que 0.");
        }
        if (discount < 0 || discount > 100) {
            throw new ValidationException("El descuento debe estar entre 0 y 100.");
        }

        this.id = id;
        this.productRef = productRef.trim();
        this.productName = productName.trim();
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.discount = discount;
        this.order = order; // Establece la relación con el padre
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
    public Order getOrder() { return order; }

    // Setters
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public void setAmount(int amount) { this.amount = amount; }
    public void setDiscount(double discount) { this.discount = discount; }
    public void setOrder(Order order) { this.order = order; }

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
