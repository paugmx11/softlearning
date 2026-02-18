package com.example.softlearning.applicationcore.entity.book.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class BookDTO {

    @Id
    @Column(name = "ident")
    private int id;

    @Column(name = "edition")
    private String edition;

    @Column(name = "title")
    private String title;

    @Column(name = "writer")
    private String writer;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private double price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "available")
    private boolean available;

    protected BookDTO() {

    }

    public BookDTO(int id, String edition, String title, String writer,
                   String description, String type, double price,
                   int stock, boolean available) {
        this.id = id;
        this.edition = edition;
        this.title = title;
        this.writer = writer;
        this.description = description;
        this.type = type;
        this.price = price;
        this.stock = stock;
        this.available = available;
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

    @Override
    public String toString() {
        return "BookDTO [" +
                "id=" + id +
                ", edition=" + edition +
                ", title=" + title +
                ", writer=" + writer +
                ", description=" + description +
                ", type=" + type +
                ", price=" + price +
                ", stock=" + stock +
                ", available=" + available +
                "]";
    }
}
