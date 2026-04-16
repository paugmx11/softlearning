package com.example.softlearning.applicationcore.entity.book.mappers;

import com.example.softlearning.applicationcore.entity.book.dtos.BookDTO;
import com.example.softlearning.sharedkernel.model.exceptions.BuildException;

public class BookMapper {

    private BookMapper() {}

    public static void bookFromDTO(BookDTO book) throws BuildException {
        if (book == null) {
            throw new BuildException("Book DTO is required.");
        }

        StringBuilder errors = new StringBuilder();
        if (book.getId() <= 0) {
            errors.append("Id no valido. ");
        }
        if (book.getTitle() == null || book.getTitle().trim().length() < 3) {
            errors.append("El titulo debe tener al menos 3 caracteres. ");
        }
        if (book.getWriter() == null || book.getWriter().trim().isEmpty()) {
            errors.append("El autor es requerido. ");
        }
        if (book.getType() == null || book.getType().trim().isEmpty()) {
            errors.append("El tipo es requerido. ");
        }
        if (Double.isNaN(book.getPrice()) || Double.isInfinite(book.getPrice()) || book.getPrice() <= 0) {
            errors.append("El precio debe ser mayor que 0. ");
        }
        if (book.getStock() < 0) {
            errors.append("El stock no puede ser negativo. ");
        }

        if (errors.length() > 0) {
            throw new BuildException(errors.toString().trim());
        }
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

