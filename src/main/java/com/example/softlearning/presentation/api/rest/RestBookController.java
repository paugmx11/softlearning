package com.example.softlearning.presentation.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.softlearning.applicationcore.entity.book.appservices.BookServices;
import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;

@RestController
@RequestMapping("/softlearning/books")
public class RestBookController {

    @Autowired
    BookServices bookServices;

    @GetMapping(value = "/{ident}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJsonBookById(@PathVariable(value = "ident") Integer ident) {
        try {
            return ResponseEntity.ok(bookServices.getByIdToJson(ident));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> listAllBooks() {
        try {
            return ResponseEntity.ok(bookServices.listAllToJson());
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> searchBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
        try {
            return ResponseEntity.ok(bookServices.searchToJson(title, type, minPrice, maxPrice));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{ident}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getXmlBookById(@PathVariable(value = "ident") Integer ident) {
        try {
            return ResponseEntity.ok(bookServices.getByIdToXml(ident));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newBookFromJson(@RequestBody String bookdata) {
        try {
            return ResponseEntity.ok(bookServices.addFromJson(bookdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> newBookFromXml(@RequestBody String bookdata) {
        try {
            return ResponseEntity.ok(bookServices.addFromXml(bookdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{ident}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateBookFromJson(@PathVariable(value = "ident") Integer ident,
            @RequestBody String bookdata) {
        try {
            return ResponseEntity.ok(bookServices.updateOneFromJson(bookdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{ident}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> updateBookFromXml(@PathVariable(value = "ident") Integer ident,
            @RequestBody String bookdata) {
        try {
            return ResponseEntity.ok(bookServices.updateOneFromXml(bookdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{ident}")
    public ResponseEntity<String> deleteByID(@PathVariable(value = "ident") Integer ident) {
        try {
            bookServices.deleteById(ident);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
