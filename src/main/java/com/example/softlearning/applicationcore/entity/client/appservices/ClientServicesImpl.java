package com.example.softlearning.applicationcore.entity.client.appservices;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.softlearning.applicationcore.entity.client.model.Client;
import com.example.softlearning.infraestructure.persistence.jpa.JpaClientRepository;
import com.example.softlearning.infraestructure.persistence.jpa.dtos.ClientDTOJPA;
import com.example.softlearning.infraestructure.persistence.jpa.mappers.ClientMapperJPA;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.sharedkernel.model.exceptions.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ClientServicesImpl implements ClientServices {

    @Autowired
    private JpaClientRepository clientRepository;

    private Serializer<ClientDTOJPA> serializer;
    private final ObjectMapper mapper = new ObjectMapper();

    protected Client getEntity(Integer id) {
        return clientRepository.findById(id).orElse(null);
    }

    protected Client getById(Integer id) throws ServiceException {
        Client client = this.getEntity(id);
        if (client == null) {
            throw new ServiceException("client " + id + " not found");
        }
        return client;
    }

    protected ClientDTOJPA getByIdAsDTO(Integer id) throws ServiceException {
        return ClientMapperJPA.toDTO(this.getById(id));
    }

    protected ClientDTOJPA parseAndValidateInput(String clientText) throws Exception {
        ClientDTOJPA dto = (ClientDTOJPA) this.serializer.deserialize(clientText, ClientDTOJPA.class);
        validateDomainData(dto);
        return dto;
    }

    protected void validateDomainData(ClientDTOJPA dto) throws BuildException {
        Client.getInstance(
                dto.getName(),
                dto.getId(),
                dto.getBirthday(),
                dto.getAddress(),
                dto.getPhone(),
                dto.getCreditCard(),
                dto.getPassword(),
                dto.getCode(),
                dto.getPremium()
        );
    }

    protected ClientDTOJPA newClient(String clientText) throws Exception {
        ClientDTOJPA dto;
        try {
            dto = this.parseAndValidateInput(clientText);
        } catch (BuildException e) {
            dto = (ClientDTOJPA) this.serializer.deserialize(clientText, ClientDTOJPA.class);
        }

        if (this.getEntity(dto.getId()) != null) {
            throw new ServiceException("client " + dto.getId() + " already exists");
        }

        Client saved = clientRepository.save(ClientMapperJPA.toEntity(dto));
        return ClientMapperJPA.toDTO(saved);
    }

    protected ClientDTOJPA updateClient(String clientText) throws Exception {
        ClientDTOJPA dto;
        try {
            dto = this.parseAndValidateInput(clientText);
        } catch (BuildException e) {
            dto = (ClientDTOJPA) this.serializer.deserialize(clientText, ClientDTOJPA.class);
        }

        this.getById(dto.getId());
        Client saved = clientRepository.save(ClientMapperJPA.toEntity(dto));
        return ClientMapperJPA.toDTO(saved);
    }

    protected String toJson(Object value) throws ServiceException {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new ServiceException("Error serializing client: " + e.getMessage());
        }
    }

    @Override
    public String getByIdToJson(Integer id) throws ServiceException {
        try {
            return SerializersCatalog.getInstance(Serializers.JSON_CLIENT)
                    .serialize(this.getByIdAsDTO(id));
        } catch (Exception e) {
            throw new ServiceException("Error serializing client: " + e.getMessage());
        }
    }

    @Override
    public String getByIdToXml(Integer id) throws ServiceException {
        try {
            return SerializersCatalog.getInstance(Serializers.XML_CLIENT)
                    .serialize(this.getByIdAsDTO(id));
        } catch (Exception e) {
            throw new ServiceException("Error serializing client: " + e.getMessage());
        }
    }

    @Override
    public String listAllToJson() throws ServiceException {
        List<ClientDTOJPA> clients = clientRepository.findAll().stream()
                .map(ClientMapperJPA::toDTO)
                .collect(Collectors.toList());
        return this.toJson(clients);
    }

    @Override
    public String searchByNameToJson(String name) throws ServiceException {
        String normalizedName = (name == null || name.isBlank()) ? null : name;
        List<ClientDTOJPA> clients = (normalizedName == null ? clientRepository.findAll() : clientRepository.findByPartialName(normalizedName))
                .stream()
                .map(ClientMapperJPA::toDTO)
                .collect(Collectors.toList());
        return this.toJson(clients);
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
        this.getById(id);
        clientRepository.deleteById(id);
    }
}
