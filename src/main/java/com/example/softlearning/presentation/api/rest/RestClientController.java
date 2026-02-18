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

import com.example.softlearning.applicationcore.entity.client.appservices.ClientServices;
import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;

@RestController
@RequestMapping("/softlearning/clients")
public class RestClientController {

    @Autowired
    ClientServices clientServices;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJsonClientById(@PathVariable(value = "id") Integer id) {
        try {
            return ResponseEntity.ok(clientServices.getByIdToJson(id));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> listAllClients() {
        try {
            return ResponseEntity.ok(clientServices.listAllToJson());
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> searchClients(
            @RequestParam(value = "name", required = false) String name) {
        try {
            return ResponseEntity.ok(clientServices.searchByNameToJson(name));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getXmlClientById(@PathVariable(value = "id") Integer id) {
        try {
            return ResponseEntity.ok(clientServices.getByIdToXml(id));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newClientFromJson(@RequestBody String clientdata) {
        try {
            return ResponseEntity.ok(clientServices.addFromJson(clientdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> newClientFromXml(@RequestBody String clientdata) {
        try {
            return ResponseEntity.ok(clientServices.addFromXml(clientdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateClientFromJson(@PathVariable(value = "id") Integer id,
            @RequestBody String clientdata) {
        try {
            return ResponseEntity.ok(clientServices.updateOneFromJson(clientdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> updateClientFromXml(@PathVariable(value = "id") Integer id,
            @RequestBody String clientdata) {
        try {
            return ResponseEntity.ok(clientServices.updateOneFromXml(clientdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteByID(@PathVariable(value = "id") Integer id) {
        try {
            clientServices.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
