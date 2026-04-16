package com.example.softlearning.applicationcore.entity.order.mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.softlearning.applicationcore.entity.order.dtos.OrderDTO;
import com.example.softlearning.applicationcore.entity.order.dtos.OrderDetailDTO;
import com.example.softlearning.applicationcore.entity.order.model.OrderStatus;
import com.example.softlearning.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.sharedkernel.model.exceptions.ValidationException;

public class OrderMapper {

    private OrderMapper() {}

    public static void orderFromDTO(OrderDTO order) throws BuildException, ValidationException {
        if (order == null) {
            throw new BuildException("El pedido es obligatorio.");
        }
        if (order.getId() <= 0) {
            throw new BuildException("El identificador del pedido debe ser mayor que 0.");
        }
        if (order.getClientId() <= 0) {
            throw new BuildException("El identificador del cliente debe ser mayor que 0.");
        }
        if (order.getOrderDate() == null) {
            throw new BuildException("La fecha del pedido es obligatoria.");
        }
        if (order.getOrderDate().isAfter(LocalDateTime.now())) {
            throw new BuildException("La fecha del pedido no puede ser futura.");
        }
        if (order.getDescription() == null || order.getDescription().trim().isEmpty()) {
            throw new BuildException("La descripcion del pedido es obligatoria.");
        }
        if (order.getStatus() == null || order.getStatus().isBlank()) {
            throw new BuildException("El estado del pedido es obligatorio.");
        }

        OrderStatus.valueOf(order.getStatus().trim().toUpperCase());

        List<OrderDetailDTO> details = order.getDetails() == null ? null : new ArrayList<>(order.getDetails());
        if (details == null) {
            order.setDetails(new ArrayList<>());
            order.setTotalAmount(0.0);
            return;
        }

        double total = 0.0;
        for (OrderDetailDTO detail : details) {
            OrderDetailMapper.validate(detail);
            total += detail.getSubtotal();
        }

        order.setDetails(details);
        order.setTotalAmount(Math.round(total * 100.0) / 100.0);
    }

    public static OrderDTO copy(OrderDTO source) {
        if (source == null) {
            return null;
        }

        List<OrderDetailDTO> details = new ArrayList<>();
        for (OrderDetailDTO detail : source.getDetails()) {
            details.add(OrderDetailMapper.copy(detail));
        }

        return new OrderDTO(
                source.getId(),
                source.getClientId(),
                source.getOrderDate(),
                source.getDeliveryDate(),
                source.getStatus(),
                source.getDescription(),
                source.getTotalAmount(),
                details
        );
    }
}
