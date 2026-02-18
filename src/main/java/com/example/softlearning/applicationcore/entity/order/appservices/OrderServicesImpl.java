package com.example.softlearning.applicationcore.entity.order.appservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.softlearning.applicationcore.entity.order.dtos.OrderDTO;
import com.example.softlearning.applicationcore.entity.order.mappers.OrderMapper;
import com.example.softlearning.infraestructure.persistence.jpa.JpaOrderRepository;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;

@Controller
public class OrderServicesImpl implements OrderServices {

    @Autowired
    private JpaOrderRepository orderRepository;
    private Serializer<OrderDTO> serializer;

    // ****** Implementing the business logic methods and common featues (clean code design) ******

    protected OrderDTO getDTO(int id)  {
        return orderRepository.findById(id).orElse(null );
    }


    protected OrderDTO getById(int id) throws ServiceException {
        OrderDTO odto = this.getDTO(id);

        if ( odto == null ) {
            throw new ServiceException("order " + id + " not found");
        }
        return odto;
    }
    
    
    protected OrderDTO checkInputData(String order) throws ServiceException, Exception {
        try {
            OrderDTO odto = (OrderDTO) this.serializer.deserialize(order, OrderDTO.class);
            OrderMapper.orderFromDTO(odto);
            return odto;
        } catch (Exception e) {
            throw new ServiceException("error in the input order data: " + e.getMessage());
        }
    }


    protected OrderDTO newOrder(String order) throws ServiceException, Exception {
        OrderDTO odto = this.checkInputData(order);
          
        if (this.getDTO(odto.getId()) == null) {
            return orderRepository.save(odto);
        } 
        throw new ServiceException("order " + odto.getId() + " already exists");
    }


    protected OrderDTO updateOrder(String order) throws ServiceException, Exception {
        try {
            OrderDTO odto = this.checkInputData(order);
            this.getById(odto.getId());
            return orderRepository.save(odto);
        } catch (ServiceException e) {
            throw e;
        }
    }



    // ****** Implementing the interface methods ******

    @Override
    public String getByIdToJson(int id) throws ServiceException {
        try {
            return SerializersCatalog.getInstance(Serializers.JSON_ORDER)
                    .serialize(this.getById(id));
        } catch (Exception e) {
            throw new ServiceException("Error serializing order: " + e.getMessage());
        }
    }


    @Override
    public String getByIdToXml(int id) throws ServiceException {
        try {
            return SerializersCatalog.getInstance(Serializers.XML_ORDER)
                    .serialize(this.getById(id));
        } catch (Exception e) {
            throw new ServiceException("Error serializing order: " + e.getMessage());
        }
    }

    
    @Override
    public String addFromJson(String order) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_ORDER);
            return serializer.serialize(this.newOrder(order));
        } catch (Exception e) {
            throw new ServiceException("Error serializing order: " + e.getMessage());
        }
    }


    @Override
    public String addFromXml(String order) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_ORDER);
            return serializer.serialize(this.newOrder(order));
        } catch (Exception e) {
            throw new ServiceException("Error serializing order: " + e.getMessage());
        }
    }

    @Override
    public String updateOneFromJson(String order) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_ORDER);
            return serializer.serialize(this.updateOrder(order));
        } catch (Exception e) {
            throw new ServiceException("Error serializing order: " + e.getMessage());
        }
    }


    @Override
    public String updateOneFromXml(String order) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_ORDER);
            return serializer.serialize(this.updateOrder(order));
        } catch (Exception e) {
            throw new ServiceException("Error serializing order: " + e.getMessage());
        }
    }


    @Override
    public void deleteById(int id) throws ServiceException {
        try {
            this.getById(id);
            orderRepository.deleteById(id);
        } catch (ServiceException e) {
            throw e;
        }
    }
}
