package com.example.softlearning.applicationcore.entity.book.dtos;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "libro")
public class BookSpanishDTO {
    @JacksonXmlProperty(localName = "identificador")
    private int id;;

    @JacksonXmlProperty(localName = "edicion")
    private String edition;

    @JacksonXmlProperty(localName = "titulo")
    private String title;

    @JacksonXmlProperty(localName = "autor")
    private String writer;

    @JacksonXmlProperty(localName = "descripcion")
    private String description;

    @JacksonXmlProperty(localName = "tipo")
    private String type;

    @JacksonXmlProperty(localName = "precio")
    private double price;

    @JacksonXmlProperty(localName = "inventario")
    private int stock;

    @JacksonXmlProperty(localName = "disponible")
    private boolean available;

        public BookSpanishDTO() {
    }
    @JsonGetter("edicion")
    public String getEdition() {
        return edition;
    }
    @JsonSetter("edicion")
    public void setEdition(String edition) {
        this.edition = edition;
    }
    @JsonGetter("identificador")
    public int getid() {
        return id;
    }
    @JsonSetter("identificador")
    public void setid(int id) {
        this.id = id;
    }
    @JsonGetter("titulo")
    public String getTitle() {
        return title;
    }
    @JsonSetter("titulo")
    public void setTitle(String title) {
        this.title = title;
    }
    @JsonGetter("autor")
    public String getWriter() {
        return writer;
    }
    @JsonSetter("autor")
    public void setWriter(String writer) {
        this.writer = writer;
    }
    @JsonGetter("descripcion")
    public String getDescription() {
        return description;
    }
    @JsonSetter("descripcion")
    public void setDescription(String description) {
        this.description = description;
    }
    @JsonGetter("tipo")
    public String getType() {
        return type;
    }
    @JsonSetter("tipo")
    public void setType(String type) {
        this.type = type;
    }
    @JsonGetter("precio")
    public double getPrice() {
        return price;
    }
    @JsonSetter("precio")
    public void setPrice(double price) {
        this.price = price;
    }
    @JsonGetter("inventario")
    public int getStock() {
        return stock;
    }
    @JsonSetter("inventario")
    public void setStock(int stock) {
        this.stock = stock;
    }
    @JsonGetter("disponible")
    public boolean isAvailable() {
        return available;
    }
    @JsonSetter("disponible")
    public void setAvailable(boolean available) {
        this.available = available;
    }
@Override
public String toString() {
    return "BookSpanishDTO [titulo" + title + ", autor=" + writer + ", descripcion=" + description + ", tipo=" + type + ", precio=" + price
            + ", inventario=" + stock + ", disponible=" + available + "]";
}
}
