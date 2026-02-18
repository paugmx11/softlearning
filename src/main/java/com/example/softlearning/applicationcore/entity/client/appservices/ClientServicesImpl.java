package com.example.softlearning.applicationcore.entity.client.appservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.softlearning.applicationcore.entity.client.dtos.ClientDTO;
import com.example.softlearning.applicationcore.entity.client.mappers.ClientMapper;
import com.example.softlearning.infraestructure.persistence.jpa.JpaClientRepository;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ClientServicesImpl implements ClientServices {

    @Autowired
    private JpaClientRepository clientRepository;
    private Serializer<ClientDTO> serializer;
    private final ObjectMapper mapper = new ObjectMapper();

    protected ClientDTO getDTO(Integer id)  {
        return clientRepository.findById(id).orElse(null );
    }


    protected ClientDTO getById(Integer id) throws ServiceException {
        ClientDTO cdto = this.getDTO(id);

        if ( cdto == null ) {
            throw new ServiceException("client " + id + " not found");
        }
        return cdto;
    }
    
    
    protected ClientDTO checkInputData(String client) throws ServiceException, Exception {
        try {
            ClientDTO cdto = (ClientDTO) this.serializer.deserialize(client, ClientDTO.class);
            ClientMapper.clientFromDTO(cdto);
            return cdto;
        } catch (Exception e) {
            throw new ServiceException("error in the input client data: " + e.getMessage());
        }
    }


    protected ClientDTO newClient(String client) throws ServiceException, Exception {
        ClientDTO cdto = this.checkInputData(client);
          
        if (this.getDTO(cdto.getId()) == null) {
            return clientRepository.save(cdto);
        } 
        throw new ServiceException("client " + cdto.getId() + " already exists");
    }


    protected ClientDTO updateClient(String client) throws ServiceException, Exception {
        try {
            ClientDTO cdto = this.checkInputData(client);
            this.getById(cdto.getId());
            return clientRepository.save(cdto);
        } catch (ServiceException e) {
            throw e;
        }
    }

    protected String toJson(Object value) throws ServiceException {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new ServiceException("Error serializing client: " + e.getMessage());
        }
    }



    // ****** Implementing the interface methods ******

    @Override
    public String getByIdToJson(Integer id) throws ServiceException {
        try {
            return SerializersCatalog.getInstance(Serializers.JSON_CLIENT)
                    .serialize(this.getById(id));
        } catch (Exception e) {
            throw new ServiceException("Error serializing client: " + e.getMessage());
        }
    }


    @Override
    public String getByIdToXml(Integer id) throws ServiceException {
        try {
            return SerializersCatalog.getInstance(Serializers.XML_CLIENT)
                    .serialize(this.getById(id));
        } catch (Exception e) {
            throw new ServiceException("Error serializing client: " + e.getMessage());
        }
    }

    @Override
    public String listAllToJson() throws ServiceException {
        return this.toJson(clientRepository.findAll());
    }

    @Override
    public String searchByNameToJson(String name) throws ServiceException {
        String normalizedName = (name == null || name.isBlank()) ? null : name;
        if (normalizedName == null) {
            return this.toJson(clientRepository.findAll());
        }
        return this.toJson(clientRepository.findByPartialName(normalizedName));
    }

    
    @Override
    public String addFromJson(String client) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_CLIENT);
            return serializer.serialize(this.newClient(client));
        } catch (Exception e) {
            throw new ServiceException("Error serializing client: " + e.getMessage());
        }
    }


    @Override
    public String addFromXml(String client) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_CLIENT);
            return serializer.serialize(this.newClient(client));
        } catch (Exception e) {
            throw new ServiceException("Error serializing client: " + e.getMessage());
        }
    }

    @Override
    public String updateOneFromJson(String client) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_CLIENT);
            return serializer.serialize(this.updateClient(client));
        } catch (Exception e) {
            throw new ServiceException("Error serializing client: " + e.getMessage());
        }
    }


    @Override
    public String updateOneFromXml(String client) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_CLIENT);
            return serializer.serialize(this.updateClient(client));
        } catch (Exception e) {
            throw new ServiceException("Error serializing client: " + e.getMessage());
        }
    }


    @Override
    public void deleteById(Integer id) throws ServiceException {
        try {
            this.getById(id);
            clientRepository.deleteById(id);
        } catch (ServiceException e) {
            throw e;
        }
    }
}
