package testserializers;

import com.example.softlearning.applicationcore.entity.book.dtos.BookDTO;
import com.example.softlearning.sharedkernel.appservices.serializers.JacksonSerializer;

public class T1SpecificJsonSerializers {

    public static void main(String[] args) {

        BookDTO book = new BookDTO(
                1,
                "1st",
                "Sapiens",
                "Yuval Harari",
                "Historia de la humanidad",
                "History",
                29.99,
                300,
                true
        );

        try {
            JacksonSerializer<BookDTO> serializer = new JacksonSerializer<>();

            String json = serializer.serialize(book);
            System.out.println(json);

            BookDTO copy = serializer.deserialize(json, BookDTO.class);
            System.out.println(copy);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
