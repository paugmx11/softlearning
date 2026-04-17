package com.example.softlearning.applicationcore.entity.order.appservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.softlearning.applicationcore.entity.order.model.Order;
import com.example.softlearning.applicationcore.entity.order.model.OrderStatus;
import com.example.softlearning.infraestructure.persistence.jpa.JpaBookRepository;
import com.example.softlearning.infraestructure.persistence.jpa.JpaClientRepository;
import com.example.softlearning.infraestructure.persistence.jpa.JpaOrderRepository;
import com.example.softlearning.infraestructure.persistence.jpa.dtos.OrderDTOJPA;
import com.example.softlearning.infraestructure.persistence.jpa.dtos.OrderDetailDTOJPA;
import com.example.softlearning.infraestructure.persistence.jpa.mappers.OrderMapperJPA;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;

import jakarta.transaction.Transactional;

@Service
public class OrderServicesImpl implements OrderServices {

    private static final double ROUNDING_FACTOR = 100.0;

    @Autowired
    private JpaOrderRepository orderRepository;

    @Autowired
    private JpaClientRepository clientRepository;

    @Autowired
    private JpaBookRepository bookRepository;

    private Serializer<OrderDTOJPA> serializer;

    @Override
    public OrderDTOJPA getByIdToDTOJPA(Integer id) throws ServiceException {
        if (id == null || id <= 0) {
            throw new ServiceException("El id del pedido debe ser mayor que 0.");
        }

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ServiceException("No se encontro el pedido con ID: " + id));

        return OrderMapperJPA.toDTO(order);
    }

    protected Order getById(int id) throws ServiceException {
        if (id <= 0) {
            throw new ServiceException("El id del pedido debe ser mayor que 0.");
        }

        return orderRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Order " + id + " not found"));
    }

    @Override
    public String getByIdToJson(int id) throws ServiceException {
        try {
            return SerializersCatalog.getInstance(Serializers.JSON_ORDER)
                    .serialize(OrderMapperJPA.toDTO(this.getById(id)));
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public String getByIdToXml(int id) throws ServiceException {
        try {
            return SerializersCatalog.getInstance(Serializers.XML_ORDER)
                    .serialize(OrderMapperJPA.toDTO(this.getById(id)));
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error XML: " + e.getMessage(), e);
        }
    }

    @Override
    public String addFromJson(String orderJson) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_ORDER);
            OrderDTOJPA dto = (OrderDTOJPA) this.serializer.deserialize(orderJson, OrderDTOJPA.class);
            OrderDTOJPA validatedDto = validateForCreate(dto);
            Order saved = orderRepository.save(OrderMapperJPA.toEntity(validatedDto));
            return serializer.serialize(OrderMapperJPA.toDTO(saved));
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error adding from JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public String addFromXml(String orderXml) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_ORDER);
            OrderDTOJPA dto = (OrderDTOJPA) this.serializer.deserialize(orderXml, OrderDTOJPA.class);
            OrderDTOJPA validatedDto = validateForCreate(dto);
            Order saved = orderRepository.save(OrderMapperJPA.toEntity(validatedDto));
            return serializer.serialize(OrderMapperJPA.toDTO(saved));
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error adding from XML: " + e.getMessage(), e);
        }
    }

    @Override
    public String updateOneFromJson(int id, String orderJson) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_ORDER);
            OrderDTOJPA dto = (OrderDTOJPA) this.serializer.deserialize(orderJson, OrderDTOJPA.class);
            OrderDTOJPA validatedDto = validateForUpdate(id, dto);
            Order saved = orderRepository.save(OrderMapperJPA.toEntity(validatedDto));
            return serializer.serialize(OrderMapperJPA.toDTO(saved));
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error updating from JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public String updateOneFromXml(int id, String orderXml) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_ORDER);
            OrderDTOJPA dto = (OrderDTOJPA) this.serializer.deserialize(orderXml, OrderDTOJPA.class);
            OrderDTOJPA validatedDto = validateForUpdate(id, dto);
            Order saved = orderRepository.save(OrderMapperJPA.toEntity(validatedDto));
            return serializer.serialize(OrderMapperJPA.toDTO(saved));
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error updating from XML: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deleteById(int id) throws ServiceException {
        Order order = this.getById(id);
        orderRepository.delete(order);
    }

    private OrderDTOJPA validateForCreate(OrderDTOJPA dto) throws ServiceException {
        validateDto(dto);

        if (orderRepository.findById(dto.getId()).isPresent()) {
            throw new ServiceException("Ya existe un pedido con id " + dto.getId() + ".");
        }

        return normalizeDto(dto);
    }

    private OrderDTOJPA validateForUpdate(int pathId, OrderDTOJPA dto) throws ServiceException {
        if (pathId <= 0) {
            throw new ServiceException("El id de la URL debe ser mayor que 0.");
        }

        validateDto(dto);

        if (dto.getId() != pathId) {
            throw new ServiceException("El id de la URL no coincide con el id del body.");
        }

        Order existingOrder = this.getById(pathId);
        validateStatusTransition(existingOrder.getStatus(), OrderStatus.valueOf(dto.getStatus().trim().toUpperCase()));
        return normalizeDto(dto);
    }

    private void validateDto(OrderDTOJPA dto) throws ServiceException {
        if (dto == null) {
            throw new ServiceException("El pedido es obligatorio.");
        }
        if (dto.getId() <= 0) {
            throw new ServiceException("El id del pedido debe ser mayor que 0.");
        }
        if (dto.getClientId() <= 0) {
            throw new ServiceException("El clientId debe ser mayor que 0.");
        }
        if (!clientRepository.existsById(dto.getClientId())) {
            throw new ServiceException("No existe el cliente con id " + dto.getClientId() + ".");
        }
        if (dto.getOrderDate() == null || dto.getOrderDate().isBlank()) {
            throw new ServiceException("La fecha del pedido es obligatoria.");
        }
        if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
            throw new ServiceException("La descripcion del pedido es obligatoria.");
        }
        if (dto.getStatus() == null || dto.getStatus().isBlank()) {
            throw new ServiceException("El estado del pedido es obligatorio.");
        }

        try {
            OrderStatus.valueOf(dto.getStatus().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ServiceException("El estado '" + dto.getStatus() + "' no es valido.");
        }

        validateDetails(dto.getDetails());
        validateTotalAmount(dto);
    }

    private void validateDetails(List<OrderDetailDTOJPA> details) throws ServiceException {
        if (details == null) {
            return;
        }

        for (OrderDetailDTOJPA detail : details) {
            if (detail == null) {
                throw new ServiceException("Los detalles del pedido no pueden contener valores nulos.");
            }
            if (detail.getId() <= 0) {
                throw new ServiceException("El id del detalle debe ser mayor que 0.");
            }
            if (detail.getBookId() == null || detail.getBookId() <= 0) {
                throw new ServiceException("El bookId del detalle es obligatorio y debe ser mayor que 0.");
            }
            if (!bookRepository.existsById(detail.getBookId())) {
                throw new ServiceException("No existe el libro con id " + detail.getBookId() + ".");
            }
            if (detail.getProductRef() == null || detail.getProductRef().trim().isEmpty()) {
                throw new ServiceException("La referencia del producto es obligatoria.");
            }
            if (detail.getProductName() == null || detail.getProductName().trim().isEmpty()) {
                throw new ServiceException("El nombre del producto es obligatorio.");
            }
            if (detail.getUnitPrice() <= 0) {
                throw new ServiceException("El precio unitario debe ser mayor que 0.");
            }
            if (detail.getAmount() <= 0) {
                throw new ServiceException("La cantidad debe ser mayor que 0.");
            }
            if (detail.getDiscount() < 0 || detail.getDiscount() > 100) {
                throw new ServiceException("El descuento debe estar entre 0 y 100.");
            }
        }
    }

    private void validateTotalAmount(OrderDTOJPA dto) throws ServiceException {
        double expected = calculateExpectedTotal(dto.getDetails());
        double roundedProvided = round(dto.getTotalAmount());

        if (Double.compare(roundedProvided, expected) != 0) {
            throw new ServiceException(
                    "El totalAmount enviado (" + roundedProvided + ") no coincide con el calculado (" + expected + ")."
            );
        }
    }

    private OrderDTOJPA normalizeDto(OrderDTOJPA dto) {
        dto.setDescription(dto.getDescription().trim());
        dto.setStatus(dto.getStatus().trim().toUpperCase());
        dto.setTotalAmount(calculateExpectedTotal(dto.getDetails()));

        if (dto.getDetails() != null) {
            for (OrderDetailDTOJPA detail : dto.getDetails()) {
                detail.setProductRef(detail.getProductRef().trim());
                detail.setProductName(detail.getProductName().trim());
                detail.setSubtotal(calculateDetailSubtotal(detail));
            }
        }

        return dto;
    }

    private double calculateExpectedTotal(List<OrderDetailDTOJPA> details) {
        if (details == null || details.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (OrderDetailDTOJPA detail : details) {
            total += calculateDetailSubtotal(detail);
        }
        return round(total);
    }

    private double calculateDetailSubtotal(OrderDetailDTOJPA detail) {
        double subtotal = detail.getUnitPrice() * detail.getAmount();
        double discountAmount = subtotal * (detail.getDiscount() / 100.0);
        return round(subtotal - discountAmount);
    }

    private double round(double value) {
        return Math.round(value * ROUNDING_FACTOR) / ROUNDING_FACTOR;
    }

    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) throws ServiceException {
        if (currentStatus == null || newStatus == null || currentStatus == newStatus) {
            return;
        }

        if (newStatus == OrderStatus.CANCELLED) {
            if (currentStatus == OrderStatus.DELIVERED || currentStatus == OrderStatus.FINISHED) {
                throw new ServiceException("No se puede cancelar un pedido entregado o finalizado.");
            }
            return;
        }

        boolean validTransition =
                (currentStatus == OrderStatus.CREATED && newStatus == OrderStatus.CONFIRMED) ||
                (currentStatus == OrderStatus.CONFIRMED && newStatus == OrderStatus.FORTHCOMING) ||
                (currentStatus == OrderStatus.FORTHCOMING && newStatus == OrderStatus.DELIVERED) ||
                (currentStatus == OrderStatus.DELIVERED && newStatus == OrderStatus.FINISHED);

        if (!validTransition) {
            throw new ServiceException(
                    "Transicion de estado no valida: " + currentStatus + " -> " + newStatus + "."
            );
        }
    }
}
