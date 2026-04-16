package com.example.softlearning.infraestructure.persistence.jpa.dtos;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "client")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientDTOJPA {
    private Integer id;
    private String name;
    private String birthday;
    private String address;
    private String phone;
    private String password;
    private String creditCard;
    private String code;
    private Boolean premium;

    // Constructor vacío
    public ClientDTOJPA() {}

    // Constructor con campos
    public ClientDTOJPA(Integer id, String name, String birthday, String address, String phone, 
                        String password, String creditCard, String code, Boolean premium) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.creditCard = creditCard;
        this.code = code;
        this.premium = premium;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getCreditCard() { return creditCard; }
    public void setCreditCard(String creditCard) { this.creditCard = creditCard; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Boolean getPremium() { return premium; }
    public void setPremium(Boolean premium) { this.premium = premium; }
}