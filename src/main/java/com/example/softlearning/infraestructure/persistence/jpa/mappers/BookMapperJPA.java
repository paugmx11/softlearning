package com.example.softlearning.infraestructure.persistence.jpa.mappers;

import java.lang.reflect.Field;

import com.example.softlearning.applicationcore.entity.book.model.Book;
import com.example.softlearning.infraestructure.persistence.jpa.dtos.BookDTOJPA;

public class BookMapperJPA {

    private BookMapperJPA() {
    }

    public static Book toEntity(BookDTOJPA dto) {
        if (dto == null) {
            return null;
        }

        try {
            java.lang.reflect.Constructor<Book> constructor = Book.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Book book = constructor.newInstance();

            setPrivateField(book, "id", dto.getId());
            setPrivateField(book, "edition", dto.getEdition());
            setPrivateField(book, "title", dto.getTitle());
            setPrivateField(book, "writer", dto.getWriter());
            setPrivateField(book, "description", dto.getDescription());
            setPrivateField(book, "type", dto.getType());
            setPrivateField(book, "price", dto.getPrice());
            setPrivateField(book, "stock", dto.getStock());
            setPrivateField(book, "available", dto.isAvailable());

            return book;
        } catch (Exception e) {
            throw new RuntimeException("Error mapeando Book a entidad JPA: " + e.getMessage(), e);
        }
    }

    public static BookDTOJPA toDTO(Book entity) {
        if (entity == null) {
            return null;
        }

        BookDTOJPA dto = new BookDTOJPA();
        dto.setId(entity.getId());
        dto.setEdition(entity.getEdition());
        dto.setTitle(entity.getTitle());
        dto.setWriter(entity.getWriter());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setAvailable(entity.isAvailable());
        return dto;
    }

    private static void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }
}
