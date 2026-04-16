package com.example.softlearning.sharedkernel.appservices.serializers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.example.softlearning.applicationcore.entity.book.model.Book;
import com.example.softlearning.sharedkernel.model.exceptions.BuildException;

class JacksonSerializerTest {

    @Test
    void shouldSerializeAndDeserializeBookInJson() throws Exception {
        JacksonSerializer<Book> serializer = new JacksonSerializer<>();
        Book book = buildBook();

        String json = serializer.serialize(book);
        Book restored = serializer.deserialize(json, Book.class);

        assertTrue(json.contains("\"title\":\"Java Avanzado\""));
        assertEquals(book.getId(), restored.getId());
        assertEquals(book.getTitle(), restored.getTitle());
        assertEquals(book.getWriter(), restored.getWriter());
        assertEquals(book.getPrice(), restored.getPrice());
    }

    private Book buildBook() throws BuildException {
        return Book.getInstance(
                1,
                "3rd Edition",
                "Java Avanzado",
                "Ana Lopez",
                "Manual de Java",
                "Programacion",
                29.99,
                10,
                true
        );
    }
}
