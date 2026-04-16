package com.example.softlearning.sharedkernel.appservices.serializers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.example.softlearning.applicationcore.entity.book.model.Book;
import com.example.softlearning.sharedkernel.model.exceptions.BuildException;

class XmlJacksonSerializerTest {

    @Test
    void shouldSerializeAndDeserializeBookInXml() throws Exception {
        XmlJacksonSerializer<Book> serializer = new XmlJacksonSerializer<>();
        Book book = buildBook();

        String xml = serializer.serialize(book);
        Book restored = serializer.deserialize(xml, Book.class);

        assertTrue(xml.contains("<title>Java Avanzado</title>"));
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
