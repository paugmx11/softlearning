package com.example.softlearning.applicationcore.entity.order.mappers;

import com.example.softlearning.applicationcore.entity.order.dtos.OrderDTO;
import com.example.softlearning.applicationcore.entity.order.dtos.OrderSpanishDTO;

public class OrderSpanishMapper {

    private OrderSpanishMapper() {}

    // Convierte de OrderDTO a OrderSpanishDTO
    public static OrderSpanishDTO fromOrderDTO(OrderDTO order) {
        if (order == null) {
            return null;
        }

        OrderSpanishDTO spanish = new OrderSpanishDTO();
        spanish.setId(order.getId());
        spanish.setClientId(order.getClientId());
        spanish.setOrderDate(order.getOrderDate());
        spanish.setDeliveryDate(order.getDeliveryDate());
        spanish.setStatus(order.getStatus());
        spanish.setDescription(order.getDescription());
        spanish.setTotalAmount(order.getTotalAmount());

        return spanish;
    }

    public static OrderDTO toOrderDTO(OrderSpanishDTO spanish) {
        if (spanish == null) {
            return null;
        }

        OrderDTO order = new OrderDTO(
                spanish.getId(),
                spanish.getClientId(),
                spanish.getOrderDate(),
                spanish.getDeliveryDate(),
                spanish.getStatus(),
                spanish.getDescription(),
                spanish.getTotalAmount()
        );

        return order;
    }
}
