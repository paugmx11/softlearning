package com.example.softlearning.applicationcore.entity.book.dtos;

public class BookDTO {

    private int id;

    private String edition;

    private String title;

    private String writer;

    private String description;

    private String type;

    private double price;

    private int stock;

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
