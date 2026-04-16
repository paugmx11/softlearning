package com.example.softlearning.applicationcore.entity.client.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.softlearning.applicationcore.entity.client.dtos.ClientDTO;
import com.example.softlearning.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.support.TestDataFactory;

class ClientMapperTest {

    @Test
    void shouldValidateClientDtoWithRequiredFields() {
        assertDoesNotThrow(() -> ClientMapper.clientFromDTO(TestDataFactory.validClientDTO()));
    }

    @Test
    void shouldReturnExactErrorWhenClientDtoIsInvalid() {
        ClientDTO invalid = new ClientDTO(null, " ", " ", " ", " ", null, " ", " ", true);

        BuildException exception = assertThrows(
                BuildException.class,
                () -> ClientMapper.clientFromDTO(invalid)
        );

        assertEquals(
                "Client validation failed: Id is required. Name is required. Birthday is required. "
                        + "Address is required. Phone is required. Password is required. Code is required.",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnNullWhenCopyingNullClientDto() {
        assertNull(ClientMapper.copy(null));
    }

    @Test
    void shouldCopyClientDtoWithoutSharingReference() {
        ClientDTO source = TestDataFactory.validClientDTO();

        ClientDTO copy = ClientMapper.copy(source);

        assertNotSame(source, copy);
        assertEquals(source.getId(), copy.getId());
        assertEquals(source.getName(), copy.getName());
        assertEquals(source.getBirthday(), copy.getBirthday());
        assertEquals(source.getAddress(), copy.getAddress());
        assertEquals(source.getPhone(), copy.getPhone());
        assertEquals(source.getCreditCard(), copy.getCreditCard());
        assertEquals(source.getPassword(), copy.getPassword());
        assertEquals(source.getCode(), copy.getCode());
        assertEquals(source.getPremium(), copy.getPremium());
    }
}
