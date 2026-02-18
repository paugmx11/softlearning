package com.example.softlearning.applicationcore.entity.order.appservices;

import org.springframework.stereotype.Service;

import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;

@Service
public interface OrderServices {
    public String getByIdToJson (int id) throws ServiceException;
    public String getByIdToXml (int id) throws ServiceException;
    public String addFromJson (String order) throws ServiceException;
    public String addFromXml (String order) throws ServiceException;
    public String updateOneFromJson(String order) throws ServiceException;
    public String updateOneFromXml(String order) throws ServiceException;
    public void deleteById(int id) throws ServiceException;
}
