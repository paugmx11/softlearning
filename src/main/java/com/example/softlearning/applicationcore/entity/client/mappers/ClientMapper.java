package com.example.softlearning.applicationcore.entity.client.mappers;
import com.example.softlearning.applicationcore.entity.client.dtos.ClientDTO;
import com.example.softlearning.sharedkernel.model.exceptions.BuildException;

public class ClientMapper {

    private ClientMapper() {}

    public static void clientFromDTO(ClientDTO client) throws BuildException {
        if (client == null) {
            throw new BuildException("Client DTO is required.");
        }
        StringBuilder errors = new StringBuilder();
        if (client.getId() == null) {
            errors.append("Id is required. ");
        }
        if (client.getName() == null || client.getName().trim().isEmpty()) {
            errors.append("Name is required. ");
        }
        if (client.getBirthday() == null || client.getBirthday().trim().isEmpty()) {
            errors.append("Birthday is required. ");
        }
        if (client.getAddress() == null || client.getAddress().trim().isEmpty()) {
            errors.append("Address is required. ");
        }
        if (client.getPhone() == null || client.getPhone().trim().isEmpty()) {
            errors.append("Phone is required. ");
        }
        if (client.getPassword() == null || client.getPassword().trim().isEmpty()) {
            errors.append("Password is required. ");
        }
        if (client.getCode() == null || client.getCode().trim().isEmpty()) {
            errors.append("Code is required. ");
        }
        if (errors.length() > 0) {
            throw new BuildException("Client validation failed: " + errors.toString().trim());
        }
    }

    public static ClientDTO copy(ClientDTO source) {
        if (source == null) {
            return null;
        }

        return new ClientDTO(
            source.getId(),
            source.getName(),
            source.getBirthday(),
            source.getAddress(),
            source.getPhone(),
            source.getCreditCard(),
            source.getPassword(),
            source.getCode(),
            source.getPremium()
        );
    }
}

