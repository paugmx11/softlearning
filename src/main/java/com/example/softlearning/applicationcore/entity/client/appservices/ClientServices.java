package com.example.softlearning.applicationcore.entity.client.appservices;

import org.springframework.stereotype.Service;

import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;

@Service
public interface ClientServices {
    public String getByIdToJson (Integer id) throws ServiceException;
    public String getByIdToXml (Integer id) throws ServiceException;
    public String listAllToJson() throws ServiceException;
    public String searchByNameToJson(String name) throws ServiceException;
    public String addFromJson (String client) throws ServiceException;
    public String addFromXml (String client) throws ServiceException;
    public String updateOneFromJson(String client) throws ServiceException;
    public String updateOneFromXml(String client) throws ServiceException;
    public void deleteById(Integer id) throws ServiceException;
}
