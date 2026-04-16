package com.example.softlearning.applicationcore.entity.book.appservices;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.softlearning.applicationcore.entity.book.model.Book;
import com.example.softlearning.infraestructure.persistence.jpa.JpaBookRepository;
import com.example.softlearning.infraestructure.persistence.jpa.dtos.BookDTOJPA;
import com.example.softlearning.infraestructure.persistence.jpa.mappers.BookMapperJPA;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class BookServicesImpl implements BookServices {

    @Autowired
    private JpaBookRepository bookRepository;

    private Serializer<BookDTOJPA> serializer;
    private final ObjectMapper mapper = new ObjectMapper();

    protected Book getEntity(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    protected Book getById(int id) throws ServiceException {
        Book book = this.getEntity(id);
        if (book == null) {
            throw new ServiceException("book " + id + " not found");
        }
        return book;
    }

    protected BookDTOJPA getByIdAsDTO(int id) throws ServiceException {
        return BookMapperJPA.toDTO(this.getById(id));
    }

    protected BookDTOJPA parseAndValidateInput(String bookText) throws Exception {
        BookDTOJPA dto = (BookDTOJPA) this.serializer.deserialize(bookText, BookDTOJPA.class);
        validateDomainData(dto);
        return dto;
    }

    protected void validateDomainData(BookDTOJPA dto) throws BuildException {
        Book.getInstance(
                dto.getId(),
                dto.getEdition(),
                dto.getTitle(),
                dto.getWriter(),
                dto.getDescription(),
                dto.getType(),
                dto.getPrice(),
                dto.getStock(),
                dto.isAvailable()
        );
    }

    protected BookDTOJPA newBook(String bookText) throws Exception {
        BookDTOJPA dto;
        try {
            dto = this.parseAndValidateInput(bookText);
        } catch (BuildException e) {
            dto = (BookDTOJPA) this.serializer.deserialize(bookText, BookDTOJPA.class);
        }

        if (this.getEntity(dto.getId()) != null) {
            throw new ServiceException("book " + dto.getId() + " already exists");
        }

        Book saved = bookRepository.save(BookMapperJPA.toEntity(dto));
        return BookMapperJPA.toDTO(saved);
    }

    protected BookDTOJPA updateBook(String bookText) throws Exception {
        BookDTOJPA dto;
        try {
            dto = this.parseAndValidateInput(bookText);
        } catch (BuildException e) {
            dto = (BookDTOJPA) this.serializer.deserialize(bookText, BookDTOJPA.class);
        }

        this.getById(dto.getId());
        Book saved = bookRepository.save(BookMapperJPA.toEntity(dto));
        return BookMapperJPA.toDTO(saved);
    }

    protected String toJson(Object value) throws ServiceException {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new ServiceException("Error serializing book: " + e.getMessage());
        }
    }

    @Override
    public String getByIdToJson(int id) throws ServiceException {
        try {
            return SerializersCatalog.getInstance(Serializers.JSON_BOOK)
                    .serialize(this.getByIdAsDTO(id));
        } catch (Exception e) {
            throw new ServiceException("Error serializing book: " + e.getMessage());
        }
    }

    @Override
    public String getByIdToXml(int id) throws ServiceException {
        try {
            return SerializersCatalog.getInstance(Serializers.XML_BOOK)
                    .serialize(this.getByIdAsDTO(id));
        } catch (Exception e) {
            throw new ServiceException("Error serializing book: " + e.getMessage());
        }
    }

    @Override
    public String listAllToJson() throws ServiceException {
        List<BookDTOJPA> books = bookRepository.findAll().stream()
                .map(BookMapperJPA::toDTO)
                .collect(Collectors.toList());
        return this.toJson(books);
    }

    @Override
    public String searchToJson(String title, String type, Double minPrice, Double maxPrice) throws ServiceException {
        String normalizedTitle = (title == null || title.isBlank()) ? null : title;
        String normalizedType = (type == null || type.isBlank()) ? null : type;
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new ServiceException("minPrice cannot be greater than maxPrice");
        }

        List<BookDTOJPA> books = bookRepository.searchBooks(
                        normalizedTitle,
                        normalizedType,
                        minPrice,
                        maxPrice
                ).stream()
                .map(BookMapperJPA::toDTO)
                .collect(Collectors.toList());

        return this.toJson(books);
    }

    @Override
    public String addFromJson(String book) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_BOOK);
            return serializer.serialize(this.newBook(book));
        } catch (Exception e) {
            throw new ServiceException("Error serializing book: " + e.getMessage());
        }
    }

    @Override
    public String addFromXml(String book) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_BOOK);
            return serializer.serialize(this.newBook(book));
        } catch (Exception e) {
            throw new ServiceException("Error serializing book: " + e.getMessage());
        }
    }

    @Override
    public String updateOneFromJson(String book) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_BOOK);
            return serializer.serialize(this.updateBook(book));
        } catch (Exception e) {
            throw new ServiceException("Error serializing book: " + e.getMessage());
        }
    }

    @Override
    public String updateOneFromXml(String book) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_BOOK);
            return serializer.serialize(this.updateBook(book));
        } catch (Exception e) {
            throw new ServiceException("Error serializing book: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        this.getById(id);
        bookRepository.deleteById(id);
    }
}
