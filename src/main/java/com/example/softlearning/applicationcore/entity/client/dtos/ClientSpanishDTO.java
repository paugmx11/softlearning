package com.example.softlearning.applicationcore.entity.client.dtos;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
public class ClientSpanishDTO {
    @JacksonXmlProperty(localName = "id")
    private int id;
    @JacksonXmlProperty(localName = "nombre")
    private String name;

    @JacksonXmlProperty(localName = "fecha_de_nacimiento")
    private String birthday;

    @JacksonXmlProperty(localName = "direccion")
    private String address;

    @JacksonXmlProperty(localName = "telefono")
    private String phone;

    @JacksonXmlProperty(localName = "tarjeta_de_credito")
    private String creditCard;

    @JacksonXmlProperty(localName = "contrasena")
    private String password;

    @JacksonXmlProperty(localName = "codigo")
    private String code;

    @JacksonXmlProperty(localName = "premium")
    private Boolean premium;

    public ClientSpanishDTO() {
    }

    public ClientSpanishDTO(int id, String name, String birthday, String address, String phone, String creditCard,
            String password, String code, Boolean premium) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
        this.creditCard = creditCard;
        this.password = password;
        this.code = code;
        this.premium = premium;
    }
    @JsonGetter("id")
    public int getId() {
        return id;
    }
    @JsonSetter("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonGetter("nombre")
    public String getName() {
        return name;
    }
    @JsonSetter("nombre")
    public void setName(String name) {
        this.name = name;
    }
    @JsonGetter("fecha_de_nacimiento")
    public String getBirthday() {
        return birthday;
    }
    @JsonSetter("fecha_de_nacimiento")
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    @JsonGetter("direccion")
    public String getAddress() {
        return address;
    }
    @JsonSetter("direccion")
    public void setAddress(String address) {
        this.address = address;
    }
    @JsonGetter("telefono")
    public String getPhone() {
        return phone;
    }
    @JsonSetter("telefono")
    public void setPhone(String phone) {
        this.phone = phone;
    }
    @JsonGetter("tarjeta_de_credito")
    public String getCreditCard() {
        return creditCard;
    }
    @JsonSetter("tarjeta_de_credito")
    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
    @JsonGetter("contrasena")
    public String getPassword() {
        return password;
    }
    @JsonSetter("contrasena")
    public void setPassword(String password) {
        this.password = password;
    }
    @JsonGetter("codigo")
    public String getCode() {
        return code;
    }
    @JsonSetter("codigo")
    public void setCode(String code) {
        this.code = code;
    }
    @JsonGetter("premium")
    public Boolean getPremium() {
        return premium;
    }
    @JsonSetter("premium")
    public void setPremium(Boolean premium) {
        this.premium = premium;
    }
    @Override
    public String toString() {
        return "ClientSpanishDTO [id=" + id + ", nombre=" + name + ", fecha_de_nacimiento=" + birthday + ", direccion=" + address
                + ", telefono=" + phone + ", tarjeta_de_credito=" + creditCard + ", contrasena=" + password + ", codigo=" + code
                + ", premium=" + premium + "]";
    }
}
