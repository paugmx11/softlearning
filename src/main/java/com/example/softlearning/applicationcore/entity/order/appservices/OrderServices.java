package com.example.softlearning.applicationcore.entity.order.appservices;

import org.springframework.stereotype.Service;

import com.example.softlearning.infraestructure.persistence.jpa.dtos.OrderDTOJPA;
import com.example.softlearning.sharedkernel.model.exceptions.ServiceException; // Importante

@Service
public interface OrderServices {
    // Método nuevo para que el Controller funcione con JSON/XML automático
    public OrderDTOJPA getByIdToDTOJPA(Integer id) throws ServiceException;

    // Tus métodos originales
    public String getByIdToJson (int id) throws ServiceException;
    public String getByIdToXml (int id) throws ServiceException;
    public String addFromJson (String order) throws ServiceException;
    public String addFromXml (String order) throws ServiceException;
    public String updateOneFromJson(String order) throws ServiceException;
    public String updateOneFromXml(String order) throws ServiceException;
    public void deleteById(int id) throws ServiceException;
}