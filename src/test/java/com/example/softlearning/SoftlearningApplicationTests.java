package com.example.softlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.softlearning.applicationcore.entity.book.appservices.BookServicesImpl;
import com.example.softlearning.applicationcore.entity.book.dtos.BookDTO;
import com.example.softlearning.applicationcore.entity.client.appservices.ClientServicesImpl;
import com.example.softlearning.applicationcore.entity.client.dtos.ClientDTO;
import com.example.softlearning.applicationcore.entity.order.appservices.OrderServicesImpl;
import com.example.softlearning.infraestructure.persistence.jpa.JpaBookRepository;
import com.example.softlearning.infraestructure.persistence.jpa.JpaClientRepository;
import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;


@SpringBootApplication
// @EntityScan(basePackages = "com.example.softlearning.applicationcore.entity")
public class SoftlearningApplicationTests {

static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SoftlearningApplication.class, args);

        System.out.println("Printing all books with BookRepository");

        var repo = context.getBean(JpaBookRepository.class);

        System.out.println("\n *****   Books in the repository   ***** \n");
        repo.findAll().forEach(System.out::println);

        System.out.println("\n *****   Java Books by title  ***** \n");
        repo.findByTitle("Java SpringJPA").forEach(System.out::println);

        System.out.println("\n *****   Add a new Java Book  ***** \n");
        var book = new BookDTO(
                101,
                "3rd Edition",
                "Java SpringJPA",
                "Jane Doe",
                "A comprehensive guide to Java Spring and JPA.",
                "Programming",
                49.99,
                10,
                true
        );
        repo.save(book);

        System.out.println("\n *****   Java Books by partial title  ***** \n");
        repo.findByPartialTitle("Java").forEach(System.out::println);

        System.out.println("\n *****   Books by id   ***** \n");
        repo.findById(book.getId()).ifPresent(System.out::println);

        System.out.println("\n *****   Delete a Book  ***** \n");
        repo.deleteById(book.getId());

        System.out.println("\n *****   Books by id    ***** \n");
        repo.findById(book.getId()).ifPresent(System.out::println);

        System.out.println("\n *****    Java Books available: " + repo.countByPartialTitle("Java"));

        System.out.println("\n\n===========================================");
        System.out.println("Printing all clients with ClientRepository");
        System.out.println("===========================================\n");

        var clientRepo = context.getBean(JpaClientRepository.class);

        System.out.println("\n *****   Clients in the repository   ***** \n");
        clientRepo.findAll().forEach(System.out::println);

        System.out.println("\n *****   Clients by name 'John'  ***** \n");
        clientRepo.findByName("John").forEach(System.out::println);

        System.out.println("\n *****   Add a new Client  ***** \n");
        clientRepo.save(new ClientDTO(
            null,
            "John Doe",
            "1990-01-15",
            "123 Main St",
            "555-1234",
            "1234-5678-9012-3456",
            "password123",
            "CODE001",
            true
        ));

        System.out.println("\n *****   Clients by partial name 'John'  ***** \n");
        clientRepo.findByPartialName("John").forEach(System.out::println);

        System.out.println("\n *****   Update a Client  ***** \n");
        clientRepo.findById(1).ifPresentOrElse(c -> {
            c.setName("John Smith");
            c.setAddress("456 Oak Ave");
            c.setPhone("555-5678");
            c.setPassword("newpassword");
            clientRepo.save(c);
        }, () -> System.out.println("Client with id 1 not found for update."));

        System.out.println("\n *****   Client by id   ***** \n");
        clientRepo.findById(1).ifPresent(System.out::println);

        System.out.println("\n *****   Delete a Client  ***** \n");
        clientRepo.deleteById(1);

        System.out.println("\n *****   Client by id    ***** \n");
        clientRepo.findById(1).ifPresent(System.out::println);

        System.out.println("\n *****    Clients with 'John' in name: "
                + clientRepo.countByPartialName("John"));


        System.out.println("\n*****   A P P L I C A T I O N    S T A R T E D   *****\n");

        System.out.println("\n *****   A P P S E R V I C E S   B O O K S***** \n");

        System.out.println("\n *****   AppServices Books_by_id   ***** \n");
        var bookServices = context.getBean(BookServicesImpl.class);
		try {
            System.out.println("\n *****   JSON DOCUMENT   ***** \n");
            System.out.println(bookServices.getByIdToJson(141));
            System.out.println("\n *****   XML DOCUMENT   ***** \n");
            System.out.println(bookServices.getByIdToXml(141));
			bookServices.deleteById(101);
        } catch (ServiceException e) {
            System.out.println("\n - - - - "+e.getMessage()+" - - - - \n");
        }

        System.out.println("\n*****   A P P S E R V I C E S   C L I E N T S   *****\n");

        System.out.println("\n *****   AppServices Clients_by_id   ***** \n");
        var clientServices = context.getBean(ClientServicesImpl.class);
		try {
            System.out.println("\n *****   JSON DOCUMENT   ***** \n");
            System.out.println(clientServices.getByIdToJson(1));
            System.out.println("\n *****   XML DOCUMENT   ***** \n");
            System.out.println(clientServices.getByIdToXml(1));
        } catch (ServiceException e) {
            System.out.println("\n - - - - "+e.getMessage()+" - - - - \n");
        }

        System.out.println("\n*****   A P P S E R V I C E S   O R D E R S   *****\n");

        System.out.println("\n *****   AppServices Orders_by_id   ***** \n");
        var orderServices = context.getBean(OrderServicesImpl.class);
		try {
            System.out.println("\n *****   JSON DOCUMENT   ***** \n");
            System.out.println(orderServices.getByIdToJson(1));
            System.out.println("\n *****   XML DOCUMENT   ***** \n");
            System.out.println(orderServices.getByIdToXml(1));
        } catch (ServiceException e) {
            System.out.println("\n - - - - "+e.getMessage()+" - - - - \n");
        }                
    }
}
