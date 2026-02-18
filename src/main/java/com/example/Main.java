package com.example;

import com.example.softlearning.applicationcore.entity.book.dtos.BookDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("Hello world!");
        BookDTO b = new BookDTO(
    1,
    "2nd",
    "Java",
    "Princeton",
    "Spring + JPA book",
    "Programmers",
    29.99,
    10,
    true
    );
        System.out.println(b);

        ObjectMapper mapper = new ObjectMapper();
    	String jsonBook;
        try {
            jsonBook = mapper.writeValueAsString(b);
            System.out.println(jsonBook);
            BookDTO bImported = new ObjectMapper().readValue(jsonBook, BookDTO.class);
	        System.out.println(bImported);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    	
    }
} 