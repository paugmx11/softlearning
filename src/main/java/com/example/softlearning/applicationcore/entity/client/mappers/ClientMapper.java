package com.example.softlearning.applicationcore.entity.client.mappers;
import com.example.softlearning.applicationcore.entity.client.dtos.ClientDTO;

public class ClientMapper {

    private ClientMapper() {}

    public static void clientFromDTO(ClientDTO client) {
        // Validation method for Client DTO
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

