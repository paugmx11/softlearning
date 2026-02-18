package com.example.softlearning.applicationcore.entity.order.mappers;

import com.example.softlearning.applicationcore.entity.order.dtos.OrderDTO;

public class OrderMapper {

    private OrderMapper() {}

    public static void orderFromDTO(OrderDTO order) {
        // Validation method for Order DTO
    }

    public static OrderDTO copy(OrderDTO source) {
        if (source == null) {
            return null;
        }

        OrderDTO target = new OrderDTO(
                source.getId(),
                source.getClientId(),
                source.getOrderDate(),
                source.getDeliveryDate(),
                source.getStatus(),
                source.getDescription(),
                source.getTotalAmount()
        );

        return target;
    }
}
