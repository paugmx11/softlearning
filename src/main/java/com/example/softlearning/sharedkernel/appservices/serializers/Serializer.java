package com.example.softlearning.sharedkernel.appservices.serializers;

public interface Serializer<T> {
    String serialize(T object) throws Exception;
    T deserialize(String source, Class<T> object) throws Exception;
}
