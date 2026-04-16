package com.example.softlearning.infraestructure.persistence.jpa.mappers;

import java.lang.reflect.Field;

import com.example.softlearning.applicationcore.entity.client.model.Client;
import com.example.softlearning.infraestructure.persistence.jpa.dtos.ClientDTOJPA;

public class ClientMapperJPA {

    private ClientMapperJPA() {
    }

    public static Client toEntity(ClientDTOJPA dto) {
        if (dto == null) {
            return null;
        }

        try {
            java.lang.reflect.Constructor<Client> constructor = Client.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Client client = constructor.newInstance();

            setPrivateField(client, "id", dto.getId());
            setPrivateField(client, "name", dto.getName());
            setPrivateField(client, "birthday", dto.getBirthday());
            setPrivateField(client, "address", dto.getAddress());
            setPrivateField(client, "phone", dto.getPhone());
            setPrivateField(client, "password", dto.getPassword());
            setPrivateField(client, "creditCard", dto.getCreditCard());
            setPrivateField(client, "code", dto.getCode());
            setPrivateField(client, "premium", dto.getPremium());

            return client;
        } catch (Exception e) {
            throw new RuntimeException("Error mapeando Client a entidad JPA: " + e.getMessage(), e);
        }
    }

    public static ClientDTOJPA toDTO(Client entity) {
        if (entity == null) {
            return null;
        }

        ClientDTOJPA dto = new ClientDTOJPA();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setBirthday(entity.getBirthday());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setPassword(entity.getPassword());
        dto.setCreditCard(entity.getCreditCard());
        dto.setCode(entity.getCode());
        dto.setPremium(entity.getPremium());
        return dto;
    }

    private static void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = findField(object.getClass(), fieldName);
        if (field == null) {
            throw new NoSuchFieldException(fieldName);
        }
        field.setAccessible(true);
        field.set(object, value);
    }

    private static Field findField(Class<?> type, String fieldName) {
        Class<?> current = type;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }
        return null;
    }
}
