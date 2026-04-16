package com.example.softlearning.applicationcore.entity.book.persistence;

import java.util.List;
import java.util.Optional;

import com.example.softlearning.applicationcore.entity.book.dtos.BookDTO;

public interface BookRepository{

    Optional<BookDTO> findById(int id);
    BookDTO save(BookDTO bookDTO);
    void deleteById(int id);
    List<BookDTO> findByTitle(String title);
    List<BookDTO> findByTitleContaining(String title);
    Integer countByTitleContaining(String title);
}

