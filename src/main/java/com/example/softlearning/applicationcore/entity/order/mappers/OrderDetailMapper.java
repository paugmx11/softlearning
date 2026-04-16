package com.example.softlearning.applicationcore.entity.order.mappers;

import com.example.softlearning.applicationcore.entity.order.dtos.OrderDetailDTO;
import com.example.softlearning.sharedkernel.model.exceptions.ValidationException;

public class OrderDetailMapper {

    private OrderDetailMapper() {
    }

    public static void validate(OrderDetailDTO detail) throws ValidationException {
        if (detail == null) {
            throw new ValidationException("El detalle del pedido es obligatorio.");
        }
        if (detail.getId() <= 0) {
            throw new ValidationException("El identificador del detalle debe ser mayor que 0.");
        }
        if (detail.getProductRef() == null || detail.getProductRef().trim().isEmpty()) {
            throw new ValidationException("La referencia del producto es obligatoria.");
        }
        if (detail.getProductName() == null || detail.getProductName().trim().isEmpty()) {
            throw new ValidationException("El nombre del producto es obligatorio.");
        }
        if (detail.getUnitPrice() <= 0) {
            throw new ValidationException("El precio unitario debe ser mayor que 0.");
        }
        if (detail.getAmount() <= 0) {
            throw new ValidationException("La cantidad debe ser mayor que 0.");
        }
        if (detail.getDiscount() < 0 || detail.getDiscount() > 100) {
            throw new ValidationException("El descuento debe estar entre 0 y 100.");
        }
    }

    public static OrderDetailDTO copy(OrderDetailDTO source) {
        if (source == null) {
            return null;
        }

        return new OrderDetailDTO(
                source.getId(),
                source.getProductRef(),
                source.getProductName(),
                source.getUnitPrice(),
                source.getAmount(),
                source.getDiscount()
        );
    }
}
