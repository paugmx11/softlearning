package com.example.softlearning.applicationcore.entity.client.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "clients")
public class ClientDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "birthday")
    private String birthday;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "credit_card")
    private String creditCard;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "code")
    private String code;
    
    @Column(name = "premium")
    private Boolean premium;

    protected ClientDTO() {
    }
    public ClientDTO(Integer id, String name, String birthday, String address, String phone, String creditCard,
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
    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getPremium() {
        return premium;
    }

    public void setPremium(Boolean premium) {
        this.premium = premium;
    }
    @Override
    public String toString() {
        return "ClientDTO [id=" + id + ", name=" + name + ", birthday=" + birthday + ", address=" + address
                + ", phone=" + phone + ", creditCard=" + creditCard + ", password=" + password + ", code=" + code
                + ", premium=" + premium + "]";
    }
}
