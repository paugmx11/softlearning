package com.example.softlearning.applicationcore.entity.client.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.softlearning.sharedkernel.model.exceptions.BuildException;

class ClientTest {

    @Test
    void shouldCreateClientWithValidData() throws BuildException {
        Client client = Client.getInstance(
                "Ana Lopez",
                1,
                "15/01/1990",
                "Calle Mayor 10",
                "600123123",
                "1234-5678-9012-3456",
                "claveSegura",
                "CLI-001",
                true
        );

        assertEquals("Ana Lopez", client.getName());
        assertEquals(1, client.getId());
        assertEquals("15/01/1990", client.getBirthday());
        assertEquals("Calle Mayor 10", client.getAddress());
        assertEquals("600123123", client.getPhone());
        assertEquals("1234-5678-9012-3456", client.getCreditCard());
        assertEquals("CLI-001", client.getCode());
        assertEquals(true, client.getPremium());
    }

    @Test
    void shouldKeepNullCreditCardWhenNotProvided() throws BuildException {
        Client client = Client.getInstance(
                "Ana Lopez",
                1,
                "15/01/1990",
                "Calle Mayor 10",
                "600123123",
                null,
                "claveSegura",
                "CLI-001",
                true
        );

        assertEquals(null, client.getCreditCard());
    }

    @Test
    void shouldCreateClientWithPremiumFalseWhenPremiumIsNull() throws BuildException {
        Client client = Client.getInstance(
                "Ana Lopez",
                1,
                "15/01/1990",
                "Calle Mayor 10",
                "600123123",
                null,
                "claveSegura",
                "CLI-001",
                null
        );

        assertFalse(client.getPremium());
    }

    @Test
    void shouldReturnExactErrorWhenCreatingClientWithMissingRequiredData() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Client.getInstance(" ", null, " ", " ", " ", null, " ", " ", true)
        );

        assertEquals(
                "Client validation failed: Name is required. Id is required. Birthday is required. "
                        + "Address is required. Phone is required. Password is required. Code is required.",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnExactErrorWhenClientNameIsMissing() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Client.getInstance(" ", 1, "15/01/1990", "Calle Mayor 10", "600123123", null, "clave", "CLI-001", true)
        );

        assertEquals("Client validation failed: Name is required.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenClientIdIsMissing() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Client.getInstance("Ana Lopez", null, "15/01/1990", "Calle Mayor 10", "600123123", null, "clave", "CLI-001", true)
        );

        assertEquals("Client validation failed: Id is required.", exception.getMessage());
    }

    @Test
    void shouldReturnExactErrorWhenClientPasswordIsMissing() {
        BuildException exception = assertThrows(
                BuildException.class,
                () -> Client.getInstance("Ana Lopez", 1, "15/01/1990", "Calle Mayor 10", "600123123", null, " ", "CLI-001", true)
        );

        assertEquals("Client validation failed: Password is required.", exception.getMessage());
    }

    @Test
    void shouldTrimFieldsWhenCreatingClient() throws BuildException {
        Client client = Client.getInstance(
                "  Ana Lopez  ",
                1,
                " 15/01/1990 ",
                " Calle Mayor 10 ",
                " 600123123 ",
                " 1234-5678-9012-3456 ",
                "claveSegura",
                " CLI-001 ",
                true
        );

        assertEquals("Ana Lopez", client.getName());
        assertEquals("15/01/1990", client.getBirthday());
        assertEquals("Calle Mayor 10", client.getAddress());
        assertEquals("600123123", client.getPhone());
        assertEquals("1234-5678-9012-3456", client.getCreditCard());
        assertEquals("CLI-001", client.getCode());
    }

    @Test
    void shouldAllowInheritedPersonSettersWhenValuesAreValid() throws BuildException {
        Client client = Client.getInstance(
                "Ana Lopez",
                1,
                "15/01/1990",
                "Calle Mayor 10",
                "600123123",
                null,
                "claveSegura",
                "CLI-001",
                true
        );

        assertEquals("Maria Lopez", client.setName("  Maria Lopez  "));
        assertEquals(Integer.valueOf(2), client.setId(2));
        assertEquals("maria@example.com", client.setEmail("maria@example.com"));
        assertEquals("700123123", client.setPhone("700123123"));
        assertEquals("Calle Nueva 20", client.setAddress("  Calle Nueva 20  "));
        assertEquals("20/02/1991", client.setBirthday("20/02/1991"));
        assertEquals("abcd", client.setPassword("abcd"));
    }

    @Test
    void shouldReturnNullInInheritedPersonSettersWhenValuesAreInvalid() throws BuildException {
        Client client = Client.getInstance(
                "Ana Lopez",
                1,
                "15/01/1990",
                "Calle Mayor 10",
                "600123123",
                null,
                "claveSegura",
                "CLI-001",
                true
        );

        assertEquals(null, client.setName(" "));
        assertEquals(null, client.setId(null));
        assertEquals(null, client.setEmail("correo-invalido"));
        assertEquals(null, client.setPhone(" "));
        assertEquals(null, client.setAddress(" "));
        assertEquals(null, client.setBirthday("1/1/1"));
        assertEquals(null, client.setPassword("abc"));
    }
}
