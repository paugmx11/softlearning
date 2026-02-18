package com.example.softlearning.applicationcore.entity.book.appservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.softlearning.applicationcore.entity.book.dtos.BookDTO;
import com.example.softlearning.applicationcore.entity.book.mappers.BookMapper;
import com.example.softlearning.infraestructure.persistence.jpa.JpaBookRepository;
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
    private Serializer<BookDTO> serializer;
    private final ObjectMapper mapper = new ObjectMapper();

    protected BookDTO getDTO(int id)  {
        return bookRepository.findById(id).orElse(null );
    }


    protected BookDTO getById(int id) throws ServiceException {
        BookDTO bdto = this.getDTO(id);

        if ( bdto == null ) {
            throw new ServiceException("book " + id + " not found");
        }
        return bdto;
    }
    
    
    protected BookDTO checkInputData(String book) throws ServiceException, Exception {
        try {
            BookDTO bdto = (BookDTO) this.serializer.deserialize(book, BookDTO.class);
            BookMapper.bookFromDTO(bdto);
            return bdto;
        } catch (BuildException e) {
            throw new ServiceException("error in the input book data: " + e.getMessage());
        }
    }


    protected BookDTO newBook(String book) throws ServiceException, Exception {
        BookDTO bdto = this.checkInputData(book);
          
        if (this.getDTO(bdto.getId()) == null) {
            return bookRepository.save(bdto);
        } 
        throw new ServiceException("book " + bdto.getId() + " already exists");
    }


    protected BookDTO updateBook(String book) throws ServiceException, Exception {
        try {
            BookDTO bdto = this.checkInputData(book);
            this.getById(bdto.getId());
            return bookRepository.save(bdto);
        } catch (ServiceException e) {
            throw e;
        }
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
                    .serialize(this.getById(id));
        } catch (Exception e) {
            throw new ServiceException("Error serializing book: " + e.getMessage());
        }
    }


    @Override
    public String getByIdToXml(int id) throws ServiceException {
        try {
            return SerializersCatalog.getInstance(Serializers.XML_BOOK)
                    .serialize(this.getById(id));
        } catch (Exception e) {
            throw new ServiceException("Error serializing book: " + e.getMessage());
        }
    }

    @Override
    public String listAllToJson() throws ServiceException {
        return this.toJson(bookRepository.findAll());
    }

    @Override
    public String searchToJson(String title, String type, Double minPrice, Double maxPrice) throws ServiceException {
        String normalizedTitle = (title == null || title.isBlank()) ? null : title;
        String normalizedType = (type == null || type.isBlank()) ? null : type;
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new ServiceException("minPrice cannot be greater than maxPrice");
        }
        return this.toJson(bookRepository.searchBooks(
                normalizedTitle,
                normalizedType,
                minPrice,
                maxPrice
        ));
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
        try {
            this.getById(id);
            bookRepository.deleteById(id);
        } catch (ServiceException e) {
            throw e;
        }
    }
}
