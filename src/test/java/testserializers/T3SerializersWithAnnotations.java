package testserializers;
import com.example.softlearning.applicationcore.entity.client.dtos.ClientDTO;
import com.example.softlearning.applicationcore.entity.client.dtos.ClientSpanishDTO;
import com.example.softlearning.sharedkernel.appservices.serializers.JacksonSerializer;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializer;

public class T3SerializersWithAnnotations {

    public static void main(String[] args) {

        ClientDTO c = new ClientDTO(1, "Joan", "1990-01-01", "C/ Major", "600000000", "1234", "pass", "CODE", true);
        ClientSpanishDTO sc = new ClientSpanishDTO(2, "Juan", "1992-02-02", "C/ Minor", "600000001", "5678", "contraseña", "CODIGO", true);


        try {
            Serializer formatter = new JacksonSerializer<ClientDTO>();

            String jclient = formatter.serialize(c);
            System.out.println(jclient);

            ClientDTO clientDTO =
                    (ClientDTO) formatter.deserialize(jclient, ClientDTO.class);
            System.out.println(clientDTO);

            formatter = new JacksonSerializer<ClientSpanishDTO>();

            String jsclient = formatter.serialize(sc);
            System.out.println(jsclient);

            ClientSpanishDTO spanishClient =
                    (ClientSpanishDTO) formatter.deserialize(jsclient, ClientSpanishDTO.class);
            System.out.println(spanishClient);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
