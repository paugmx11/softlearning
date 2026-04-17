package com.example.softlearning.applicationcore.entity.order.appservices;

import org.springframework.stereotype.Service;

import com.example.softlearning.infraestructure.persistence.jpa.dtos.OrderDTOJPA;
import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;

@Service
public interface OrderServices {
    OrderDTOJPA getByIdToDTOJPA(Integer id) throws ServiceException;

    String getByIdToJson(int id) throws ServiceException;
    String getByIdToXml(int id) throws ServiceException;
    String addFromJson(String order) throws ServiceException;
    String addFromXml(String order) throws ServiceException;
    String updateOneFromJson(int id, String order) throws ServiceException;
    String updateOneFromXml(int id, String order) throws ServiceException;
    void deleteById(int id) throws ServiceException;
}
