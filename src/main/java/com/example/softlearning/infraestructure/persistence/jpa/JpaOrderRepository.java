package com.example.softlearning.infraestructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.softlearning.applicationcore.entity.order.dtos.OrderDTO;

import jakarta.transaction.Transactional;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderDTO, Integer> {
    public Optional<OrderDTO> findById(int id);
    
    public List<OrderDTO> findByClientId(int clientId);
    
    public List<OrderDTO> findByStatus(String status);
    
    @Transactional
    public void deleteById(int id);
}
