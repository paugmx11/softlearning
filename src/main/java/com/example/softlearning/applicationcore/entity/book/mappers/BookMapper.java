package com.example.softlearning.applicationcore.entity.book.mappers;

import com.example.softlearning.applicationcore.entity.book.dtos.BookDTO;

public class BookMapper {

    private BookMapper() {}

    public static void bookFromDTO(BookDTO book) {
        // Validation method for Book DTO
    }

    public static BookDTO copy(BookDTO source) {
        if (source == null) {
            return null;
        }

        return new BookDTO(
                source.getId(),
                source.getEdition(),
                source.getTitle(),
                source.getWriter(),
                source.getDescription(),
                source.getType(),
                source.getPrice(),
                source.getStock(),
                source.isAvailable()
        );
    }
}

