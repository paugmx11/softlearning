package com.example.softlearning.sharedkernel.appservices.serializers;

import java.util.TreeMap;

import com.example.softlearning.applicationcore.entity.book.dtos.BookDTO;
import com.example.softlearning.applicationcore.entity.client.dtos.ClientDTO;
import com.example.softlearning.applicationcore.entity.client.dtos.ClientSpanishDTO;
import com.example.softlearning.applicationcore.entity.order.dtos.OrderDTO;

public class SerializersCatalog {
    private static TreeMap<Serializers, Serializer> catalog = new TreeMap<>();

    private static void loadCatalog() {
        // AL CREAR EL SERIALITZADOR PASSEM PER CONSTRUCTOR L'OBJECTE AMB QUE ES
        // REALIZARÀ LA SERIALITZACIO
        catalog.put(Serializers.JSON_CLIENT, new JacksonSerializer<ClientDTO>());
        catalog.put(Serializers.JSON_SP_CLIENT, new JacksonSerializer<ClientSpanishDTO>());
        catalog.put(Serializers.XML_CLIENT, new XmlJacksonSerializer<ClientDTO>());
        catalog.put(Serializers.XML_SP_CLIENT, new XmlJacksonSerializer<ClientSpanishDTO>());
        catalog.put(Serializers.JSON_BOOK, new JacksonSerializer<BookDTO>());
        catalog.put(Serializers.XML_BOOK, new XmlJacksonSerializer<BookDTO>());
        catalog.put(Serializers.JSON_ORDER, new JacksonSerializer<OrderDTO>());
        catalog.put(Serializers.XML_ORDER, new XmlJacksonSerializer<OrderDTO>());
    }

    public static Serializer getInstance(Serializers type) {
        if (catalog.isEmpty()) {
            loadCatalog();
        }
        return catalog.get(type);
    }
}
