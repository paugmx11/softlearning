package com.example.softlearning.applicationcore.entity.book.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.softlearning.applicationcore.entity.book.dtos.BookDTO;
import com.example.softlearning.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.support.TestDataFactory;

class BookMapperTest {

    @Test
    void shouldValidateBookDtoWithValidData() {
        assertDoesNotThrow(() -> BookMapper.bookFromDTO(TestDataFactory.validBookDTO()));
    }

    @Test
    void shouldReturnExactErrorWhenBookDtoIsInvalid() {
        BookDTO invalid = new BookDTO(0, null, "ab", " ", null, " ", 0.0, -1, true);

        BuildException exception = assertThrows(
                BuildException.class,
                () -> BookMapper.bookFromDTO(invalid)
        );

        assertEquals(
                "Id no valido. El titulo debe tener al menos 3 caracteres. El autor es requerido. "
                        + "El tipo es requerido. El precio debe ser mayor que 0. El stock no puede ser negativo.",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnNullWhenCopyingNullBookDto() {
        assertNull(BookMapper.copy(null));
    }

    @Test
    void shouldCopyBookDtoWithoutSharingReference() {
        BookDTO source = TestDataFactory.validBookDTO();

        BookDTO copy = BookMapper.copy(source);

        assertNotSame(source, copy);
        assertEquals(source.getId(), copy.getId());
        assertEquals(source.getEdition(), copy.getEdition());
        assertEquals(source.getTitle(), copy.getTitle());
        assertEquals(source.getWriter(), copy.getWriter());
        assertEquals(source.getDescription(), copy.getDescription());
        assertEquals(source.getType(), copy.getType());
        assertEquals(source.getPrice(), copy.getPrice());
        assertEquals(source.getStock(), copy.getStock());
        assertEquals(source.isAvailable(), copy.isAvailable());
    }
}
