package com.example.softlearning.sharedkernel.model.stakeholders;

import java.time.format.DateTimeFormatter;

public abstract class Person {
    
    protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    protected String name;
    protected Integer id;
    protected String email;
    protected String phone;
    protected String address;
    protected String birthday;
    protected String password;
    
    protected  Person() {
    }
    
    public String getName() {
        return name;
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getBirthday() {
        return birthday;
    }

    
    public String getPassword() {
        return password;
    }
    
    public String setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        return this.name = name.trim();
    }
    
    public Integer setId(Integer id) {
        if (id == null) {
            return null;
        }
        return this.id = id;
    }
    
    public String setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return null;
        }
        return this.email = email.trim();
    }
    
    public String setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return null;
        }
        return this.phone = phone.trim();
    }
    
    public String setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return null;
        }
        return this.address = address.trim();
    }
    
    public String setBirthday(String birthday) {
        if (birthday == null || birthday.trim().length() < 8) {
            return null;
        }
        return this.birthday = birthday.trim();
    }
    
    public String setPassword(String password) {
        if (password == null || password.length() < 4) {
            return null;
        }
        return this.password = password;
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", birthday=" + getBirthday() +
                '}';
    }
}