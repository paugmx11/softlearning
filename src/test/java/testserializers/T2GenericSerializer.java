package testserializers;

import com.example.softlearning.applicationcore.entity.book.dtos.BookDTO;
import com.example.softlearning.applicationcore.entity.client.dtos.ClientDTO;
import com.example.softlearning.sharedkernel.appservices.serializers.JacksonSerializer;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializer;

public class T2GenericSerializer {

    public static void main(String[] args) {

        BookDTO b = new BookDTO(
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

        ClientDTO c = new ClientDTO(
            1,
                "Joan",
                "1990-01-01",
                "C/ Major",
                "600000000",
                "1234",
                "pass",
                "CODE",
                true
        );

        try {
            Serializer formatter = new JacksonSerializer<BookDTO>();

            String jbook = formatter.serialize(b);
            System.out.println(jbook);

            BookDTO copyBook = (BookDTO) formatter.deserialize(jbook, BookDTO.class);
            System.out.println(copyBook);

            formatter = new JacksonSerializer<ClientDTO>();

            String jclient;
            jclient = formatter.serialize(c);
            System.out.println(jclient);

            ClientDTO copyClient =
                    (ClientDTO) formatter.deserialize(jclient, ClientDTO.class);
            System.out.println(copyClient);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
