package com.example.softlearning.applicationcore.entity.client.persistence;

import java.util.List;
import java.util.Optional;

import com.example.softlearning.applicationcore.entity.client.dtos.ClientDTO;

public interface ClientRepository {

    Optional<ClientDTO> findById(int id);
    List<ClientDTO> findByName(String name);
    List<ClientDTO> findByNameContaining(String name);
    Integer countByNameContaining(String name);
    void deleteById(int id);
    ClientDTO save(ClientDTO client);
}
