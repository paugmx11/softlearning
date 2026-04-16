package com.example.softlearning.infraestructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.softlearning.applicationcore.entity.order.model.Order;
import com.example.softlearning.applicationcore.entity.order.model.OrderStatus;

import jakarta.transaction.Transactional;

@Repository
public interface JpaOrderRepository extends JpaRepository<Order, Integer> {
    public Optional<Order> findById(int id);
    
    public List<Order> findByClientId(int clientId);
    
    public List<Order> findByStatus(OrderStatus status);
    
    @Transactional
    public void deleteById(int id);
}
