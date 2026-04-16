package com.example.softlearning.sharedkernel.appservices.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class XmlJacksonSerializer<T> implements Serializer<T> {
    private final XmlMapper mapper;

    public XmlJacksonSerializer() {
        this.mapper = new XmlMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    
    @Override
    public String serialize(T object) throws Exception {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }
    }
    @Override
    public T deserialize(String source, Class<T> object) throws Exception {
        try {
            return mapper.readValue(source, object);
        }catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }
    }
}
