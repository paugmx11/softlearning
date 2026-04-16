package com.example.softlearning.applicationcore.entity.book.mappers;

import com.example.softlearning.applicationcore.entity.book.dtos.BookDTO;
import com.example.softlearning.applicationcore.entity.book.dtos.BookSpanishDTO;

public class BookSpanishMapper {

    private BookSpanishMapper() {}

    public static BookSpanishDTO fromBookDTO(BookDTO book) {
        if (book == null) {
            return null;
        }

        BookSpanishDTO spanish = new BookSpanishDTO();
        spanish.setid(book.getId());
        spanish.setEdition(book.getEdition());
        spanish.setTitle(book.getTitle());
        spanish.setWriter(book.getWriter());
        spanish.setDescription(book.getDescription());
        spanish.setType(book.getType());
        spanish.setPrice(book.getPrice());
        spanish.setStock(book.getStock());
        spanish.setAvailable(book.isAvailable());

        return spanish;
    }

    public static BookDTO toBookDTO(BookSpanishDTO spanish) {
        if (spanish == null) {
            return null;
        }

        return new BookDTO(
                spanish.getid(),
                spanish.getEdition(),
                spanish.getTitle(),
                spanish.getWriter(),
                spanish.getDescription(),
                spanish.getType(),
                spanish.getPrice(),
                spanish.getStock(),
                spanish.isAvailable()
        );
    }
}
