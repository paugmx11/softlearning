package com.example.softlearning.applicationcore.entity.client.model;

import com.example.softlearning.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.sharedkernel.model.stakeholders.Person;

public class Client extends Person {

    protected String creditCard;
    private String code;
    protected Boolean premium;

    private Client() {
    }

        public static Client getInstance(
            String name,
            Integer id,
            String birthday,
            String address,
            String phone,
            String creditCard,
            String password,
            String code,
            Boolean premium
        ) throws BuildException {

        Client c = new Client();
        String errorMessage = "";

        try {
            if (name == null || name.trim().isEmpty()) {
                errorMessage += "Name is required. ";
            }
            if (id == null) {
                errorMessage += "Id is required. ";
            }
            if (birthday == null || birthday.trim().isEmpty()) {
                errorMessage += "Birthday is required. ";
            }
            if (address == null || address.trim().isEmpty()) {
                errorMessage += "Address is required. ";
            }
            if (phone == null || phone.trim().isEmpty()) {
                errorMessage += "Phone is required. ";
            }
            if (password == null || password.trim().isEmpty()) {
                errorMessage += "Password is required. ";
            }
            if (code == null || code.trim().isEmpty()) {
                errorMessage += "Code is required. ";
            }

            if (!errorMessage.isEmpty()) {
                throw new BuildException("Client validation failed: " + errorMessage.trim());
            }

            c.name = name.trim();
            c.id = id;
            c.birthday = birthday.trim();
            c.address = address.trim();
            c.phone = phone.trim();
            c.password = password;
            c.creditCard = creditCard != null ? creditCard.trim() : null;
            c.code = code.trim();
            c.premium = premium != null ? premium : false;

        } catch (BuildException e) {
            throw e;
        }

        return c;
    }
    public String getAddress() {
        return address;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
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
}
