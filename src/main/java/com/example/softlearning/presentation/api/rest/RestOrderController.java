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
import org.springframework.web.bind.annotation.RestController;

import com.example.softlearning.applicationcore.entity.order.appservices.OrderServices;
import com.example.softlearning.infraestructure.persistence.jpa.dtos.OrderDTOJPA;
import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;

@RestController
@RequestMapping("/softlearning/orders")
public class RestOrderController {

    @Autowired
    private OrderServices orderServices;

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> getOrderById(@PathVariable(value = "id") Integer id) {
        try {
            OrderDTOJPA order = orderServices.getByIdToDTOJPA(id);
            return ResponseEntity.ok(order);
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newOrderFromJson(@RequestBody String orderData) {
        try {
            return ResponseEntity.ok(orderServices.addFromJson(orderData));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> newOrderFromXml(@RequestBody String orderData) {
        try {
            return ResponseEntity.ok(orderServices.addFromXml(orderData));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateOrderFromJson(@PathVariable(value = "id") Integer id,
                                                      @RequestBody String orderData) {
        try {
            return ResponseEntity.ok(orderServices.updateOneFromJson(id, orderData));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> updateOrderFromXml(@PathVariable(value = "id") Integer id,
                                                     @RequestBody String orderData) {
        try {
            return ResponseEntity.ok(orderServices.updateOneFromXml(id, orderData));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable(value = "id") Integer id) {
        try {
            orderServices.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
