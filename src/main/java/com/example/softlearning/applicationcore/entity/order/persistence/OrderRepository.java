package com.example.softlearning.applicationcore.entity.order.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.softlearning.applicationcore.entity.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByClient_Id(int clientId);
}
