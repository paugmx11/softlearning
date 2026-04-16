package com.example.softlearning.applicationcore.entity.order.appservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// USAMOS ÚNICAMENTE ESTOS MODELOS
import com.example.softlearning.applicationcore.entity.order.model.Order;
import com.example.softlearning.infraestructure.persistence.jpa.JpaOrderRepository;
import com.example.softlearning.infraestructure.persistence.jpa.dtos.OrderDTOJPA;
import com.example.softlearning.infraestructure.persistence.jpa.mappers.OrderMapperJPA;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;

@Service
public class OrderServicesImpl implements OrderServices {

    @Autowired
    private JpaOrderRepository orderRepository;
    
    private Serializer<OrderDTOJPA> serializer;

    // --- Implementación del método para el Controller ---
    @Override
    public OrderDTOJPA getByIdToDTOJPA(Integer id) throws ServiceException {
        // Buscamos la entidad (Order)
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ServiceException("No se encontró el pedido con ID: " + id));
        
        // Convertimos a DTO de transporte
        return OrderMapperJPA.toDTO(order);
    }

    // --- Métodos auxiliares ---
    protected Order getById(int id) throws ServiceException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Order " + id + " not found"));
    }

    // --- Métodos de la Interfaz (Formatos manuales) ---
    @Override
    public String getByIdToJson(int id) throws ServiceException {
        try {
            return SerializersCatalog.getInstance(Serializers.JSON_ORDER)
                    .serialize(OrderMapperJPA.toDTO(this.getById(id)));
        } catch (Exception e) {
            throw new ServiceException("Error JSON: " + e.getMessage());
        }
    }

    @Override
    public String getByIdToXml(int id) throws ServiceException {
        try {
            return SerializersCatalog.getInstance(Serializers.XML_ORDER)
                    .serialize(OrderMapperJPA.toDTO(this.getById(id)));
        } catch (Exception e) {
            throw new ServiceException("Error XML: " + e.getMessage());
        }
    }

    @Override
    public String addFromJson(String orderJson) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_ORDER);
            OrderDTOJPA dto = (OrderDTOJPA) this.serializer.deserialize(orderJson, OrderDTOJPA.class);
            Order saved = orderRepository.save(OrderMapperJPA.toEntity(dto));
            return serializer.serialize(OrderMapperJPA.toDTO(saved));
        } catch (Exception e) {
            throw new ServiceException("Error adding from JSON: " + e.getMessage());
        }
    }

    @Override
    public String addFromXml(String orderXml) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_ORDER);
            OrderDTOJPA dto = (OrderDTOJPA) this.serializer.deserialize(orderXml, OrderDTOJPA.class);
            Order saved = orderRepository.save(OrderMapperJPA.toEntity(dto));
            return serializer.serialize(OrderMapperJPA.toDTO(saved));
        } catch (Exception e) {
            throw new ServiceException("Error adding from XML: " + e.getMessage());
        }
    }

    @Override
    public String updateOneFromJson(String orderJson) throws ServiceException {
        return addFromJson(orderJson);
    }

    @Override
    public String updateOneFromXml(String orderXml) throws ServiceException {
        return addFromXml(orderXml);
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        Order order = this.getById(id);
        orderRepository.delete(order);
    }
}