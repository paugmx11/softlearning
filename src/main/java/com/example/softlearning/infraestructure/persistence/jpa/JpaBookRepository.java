package com.example.softlearning.infraestructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.softlearning.applicationcore.entity.book.model.Book;

import jakarta.transaction.Transactional;

@Repository
public interface JpaBookRepository extends JpaRepository<Book, Integer> {
    public Optional<Book> findById(int id);

    public List<Book> findByTitle(String title);
 
    @Query(value="SELECT b FROM Book b WHERE b.title LIKE %:title%")
    public List<Book> findByPartialTitle(String title);

    @Query(value="SELECT count(*) FROM Book b WHERE b.title LIKE %:title%")
    public Integer countByPartialTitle(String title);

    @Query("SELECT b FROM Book b " +
           "WHERE (:title IS NULL OR b.title LIKE CONCAT('%', :title, '%')) " +
           "AND (:type IS NULL OR b.type LIKE CONCAT('%', :type, '%')) " +
           "AND (:minPrice IS NULL OR b.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR b.price <= :maxPrice)")
    public List<Book> searchBooks(String title, String type, Double minPrice, Double maxPrice);

    @Transactional
    public void deleteById(int id);
    
}
