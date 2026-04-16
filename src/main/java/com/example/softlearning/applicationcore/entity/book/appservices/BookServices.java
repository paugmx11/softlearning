package com.example.softlearning.applicationcore.entity.book.appservices;

import org.springframework.stereotype.Service;

import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;

@Service
public interface BookServices {
    public String getByIdToJson (int id) throws ServiceException;
    public String getByIdToXml (int id) throws ServiceException;
    public String listAllToJson() throws ServiceException;
    public String searchToJson(String title, String type, Double minPrice, Double maxPrice) throws ServiceException;
    public String addFromJson (String book) throws ServiceException;
    public String addFromXml (String book) throws ServiceException;
    public String updateOneFromJson(String book) throws ServiceException;
    public String updateOneFromXml(String book) throws ServiceException;
    public void deleteById(int id) throws ServiceException;
}
