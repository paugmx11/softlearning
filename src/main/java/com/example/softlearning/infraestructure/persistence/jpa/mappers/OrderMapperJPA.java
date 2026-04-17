package com.example.softlearning.infraestructure.persistence.jpa.mappers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import com.example.softlearning.applicationcore.entity.book.model.Book;
import com.example.softlearning.applicationcore.entity.client.model.Client;
import com.example.softlearning.applicationcore.entity.order.model.Order;
import com.example.softlearning.applicationcore.entity.order.model.OrderDetail;
import com.example.softlearning.applicationcore.entity.order.model.OrderStatus;
import com.example.softlearning.infraestructure.persistence.jpa.dtos.OrderDTOJPA;
import com.example.softlearning.infraestructure.persistence.jpa.dtos.OrderDetailDTOJPA;

public class OrderMapperJPA {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private OrderMapperJPA() {
    }

    public static Order toEntity(OrderDTOJPA dto) {
        if (dto == null) {
            return null;
        }

        try {
            Constructor<Order> constructor = Order.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Order order = constructor.newInstance();

            setPrivateField(order, "id", dto.getId());
            setPrivateField(order, "client", createClientReference(dto.getClientId()));
            setPrivateField(order, "description", dto.getDescription());
            setPrivateField(order, "status", parseStatus(dto.getStatus()));
            setPrivateField(order, "orderDate", parseTimestamp(dto.getOrderDate()));
            setPrivateField(order, "deliveryDate", parseTimestamp(dto.getDeliveryDate()));
            setPrivateField(order, "totalAmount", dto.getTotalAmount());
            mapDetails(dto.getDetails(), order);

            return order;
        } catch (Exception e) {
            throw new RuntimeException("Error mapeando a entidad JPA: " + e.getMessage(), e);
        }
    }

    public static OrderDTOJPA toDTO(Order entity) {
        if (entity == null) {
            return null;
        }

        OrderDTOJPA dto = new OrderDTOJPA();
        dto.setId(entity.getId());
        dto.setClientId(entity.getClientId());
        dto.setDescription(entity.getDescription());
        dto.setTotalAmount(entity.getTotalAmount());

        if (entity.getStatus() != null) {
            dto.setStatus(entity.getStatus().name());
        }
        if (entity.getOrderDate() != null) {
            dto.setOrderDate(formatTimestamp(entity.getOrderDate()));
        }
        if (entity.getDeliveryDate() != null) {
            dto.setDeliveryDate(formatTimestamp(entity.getDeliveryDate()));
        }
        if (entity.getDetails() != null) {
            dto.setDetails(entity.getDetails().stream()
                    .map(OrderMapperJPA::toDetailDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private static OrderDetailDTOJPA toDetailDTO(OrderDetail detail) {
        OrderDetailDTOJPA dto = new OrderDetailDTOJPA();
        dto.setId(detail.getId());
        dto.setBookId(detail.getBook() != null ? detail.getBook().getId() : null);
        dto.setProductRef(detail.getProductRef());
        dto.setProductName(detail.getProductName());
        dto.setUnitPrice(detail.getUnitPrice());
        dto.setAmount(detail.getAmount());
        dto.setDiscount(detail.getDiscount());
        dto.setSubtotal(detail.getSubtotal());
        return dto;
    }

    private static void mapDetails(List<OrderDetailDTOJPA> detailDTOs, Order order) throws Exception {
        Field detailsField = findField(Order.class, "details");
        detailsField.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<OrderDetail> details = (List<OrderDetail>) detailsField.get(order);
        details.clear();

        if (detailDTOs == null || detailDTOs.isEmpty()) {
            setPrivateField(order, "detailCounter", 1);
            return;
        }

        int maxDetailId = 0;
        for (OrderDetailDTOJPA detailDTO : detailDTOs) {
            OrderDetail detail = createDetail(detailDTO, order);
            details.add(detail);
            maxDetailId = Math.max(maxDetailId, detail.getId());
        }
        setPrivateField(order, "detailCounter", maxDetailId + 1);
        setPrivateField(order, "totalAmount", details.stream().mapToDouble(OrderDetail::getSubtotal).sum());
    }

    private static OrderDetail createDetail(OrderDetailDTOJPA dto, Order order) throws Exception {
        Constructor<OrderDetail> constructor = OrderDetail.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        OrderDetail detail = constructor.newInstance();

        setPrivateField(detail, "id", dto.getId());
        setPrivateField(detail, "order", order);
        setPrivateField(detail, "book", createBookReference(dto.getBookId()));
        setPrivateField(detail, "productRef", dto.getProductRef());
        setPrivateField(detail, "productName", dto.getProductName());
        setPrivateField(detail, "unitPrice", dto.getUnitPrice());
        setPrivateField(detail, "amount", dto.getAmount());
        setPrivateField(detail, "discount", dto.getDiscount());

        return detail;
    }

    private static OrderStatus parseStatus(String rawStatus) {
        if (rawStatus == null || rawStatus.isBlank()) {
            return null;
        }
        try {
            return OrderStatus.valueOf(rawStatus.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static LocalDateTime parseTimestamp(String rawTimestamp) {
        if (rawTimestamp == null || rawTimestamp.isBlank()) {
            return null;
        }

        String normalized = rawTimestamp.trim();
        try {
            return LocalDateTime.parse(normalized);
        } catch (DateTimeParseException ignored) {
        }

        try {
            return LocalDateTime.parse(normalized, TIMESTAMP_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Formato de fecha no valido: " + rawTimestamp, e);
        }
    }

    private static String formatTimestamp(LocalDateTime value) {
        return value.format(TIMESTAMP_FORMATTER);
    }

    private static Client createClientReference(Integer clientId) throws Exception {
        if (clientId == null || clientId <= 0) {
            return null;
        }
        Constructor<Client> constructor = Client.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Client client = constructor.newInstance();
        setPrivateField(client, "id", clientId);
        return client;
    }

    private static Book createBookReference(Integer bookId) throws Exception {
        if (bookId == null || bookId <= 0) {
            return null;
        }
        Constructor<Book> constructor = Book.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Book book = constructor.newInstance();
        setPrivateField(book, "id", bookId);
        return book;
    }

    private static void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = findField(object.getClass(), fieldName);
        if (field == null) {
            throw new NoSuchFieldException(fieldName);
        }
        field.setAccessible(true);
        field.set(object, value);
    }

    private static Field findField(Class<?> type, String fieldName) {
        Class<?> current = type;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }
        return null;
    }
}
