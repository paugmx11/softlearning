package com.example.softlearning.sharedkernel.appservices.serializers;

import java.util.TreeMap;

import com.example.softlearning.applicationcore.entity.client.dtos.ClientSpanishDTO;
import com.example.softlearning.infraestructure.persistence.jpa.dtos.BookDTOJPA;
import com.example.softlearning.infraestructure.persistence.jpa.dtos.ClientDTOJPA;
import com.example.softlearning.infraestructure.persistence.jpa.dtos.OrderDTOJPA;

public class SerializersCatalog {
    private static TreeMap<Serializers, Serializer> catalog = new TreeMap<>();

    private static void loadCatalog() {
        // AL CREAR EL SERIALITZADOR PASSEM PER CONSTRUCTOR L'OBJECTE AMB QUE ES
        // REALIZARÀ LA SERIALITZACIO
        catalog.put(Serializers.JSON_CLIENT, new JacksonSerializer<ClientDTOJPA>());
        catalog.put(Serializers.JSON_SP_CLIENT, new JacksonSerializer<ClientSpanishDTO>());
        catalog.put(Serializers.XML_CLIENT, new XmlJacksonSerializer<ClientDTOJPA>());
        catalog.put(Serializers.XML_SP_CLIENT, new XmlJacksonSerializer<ClientSpanishDTO>());
        catalog.put(Serializers.JSON_BOOK, new JacksonSerializer<BookDTOJPA>());
        catalog.put(Serializers.XML_BOOK, new XmlJacksonSerializer<BookDTOJPA>());
        catalog.put(Serializers.JSON_ORDER, new JacksonSerializer<OrderDTOJPA>());
        catalog.put(Serializers.XML_ORDER, new XmlJacksonSerializer<OrderDTOJPA>());
    }

    public static Serializer getInstance(Serializers type) {
        if (catalog.isEmpty()) {
            loadCatalog();
        }
        return catalog.get(type);
    }
}
