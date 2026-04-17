package com.example.softlearning.applicationcore.entity.book.model;

import java.util.ArrayList;
import java.util.List;

import com.example.softlearning.applicationcore.entity.order.model.OrderDetail;
import com.example.softlearning.sharedkernel.model.exceptions.BuildException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "books")
public class Book {

    @Id
    private int id;

    private String edition;
    private String title;
    private String writer;

    @Column(length = 1000)
    private String description;

    private String type;
    private double price;
    private int stock;
    private boolean available;

    @JsonIgnore
    @XmlTransient
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Book() {
    }

    public static Book getInstance(int id, String edition, String title, String writer, String description, String type, double price, int stock, boolean available) throws BuildException {
        Book b = new Book();
        String errorMessage = "";

        if (id <= 0) {
            errorMessage += "Id no valido. ";
        }
        if (title == null || title.trim().length() < 3) {
            errorMessage += "El titulo debe tener al menos 3 caracteres. ";
        }
        if (writer == null || writer.trim().isEmpty()) {
            errorMessage += "El autor es requerido. ";
        }
        if (type == null || type.trim().isEmpty()) {
            errorMessage += "El tipo es requerido. ";
        }
        if (Double.isNaN(price) || Double.isInfinite(price) || price <= 0) {
            errorMessage += "El precio debe ser mayor que 0. ";
        }
        if (stock < 0) {
            errorMessage += "El stock no puede ser negativo. ";
        }
        if (stock == 0) {
            available = false;
        }
        if (!errorMessage.isEmpty()) {
            throw new BuildException(errorMessage.trim());
        }

        b.id = id;
        b.edition = edition != null ? edition.trim() : null;
        b.title = title.trim();
        b.writer = writer.trim();
        b.description = description != null ? description.trim() : null;
        b.type = type.trim();
        b.price = price;
        b.stock = stock;
        b.available = available;

        return b;
    }

    @JsonIgnore
    @XmlTransient
    public String getBookInfo() {
        return "Book: " + this.title + " by " + this.writer +
                " - Stock: " + this.stock + " - Available: " + this.available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<OrderDetail> getOrderDetails() {
        return List.copyOf(orderDetails);
    }
}
