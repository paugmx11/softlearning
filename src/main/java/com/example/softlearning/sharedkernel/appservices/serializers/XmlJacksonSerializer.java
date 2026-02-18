package com.example.softlearning.sharedkernel.appservices.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlJacksonSerializer<T> implements Serializer<T> {
    private XmlMapper mapper = new XmlMapper();
    
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