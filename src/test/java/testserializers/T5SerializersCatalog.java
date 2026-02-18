package testserializers;
import com.example.softlearning.applicationcore.entity.client.dtos.ClientDTO;
import com.example.softlearning.applicationcore.entity.client.dtos.ClientSpanishDTO;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.sharedkernel.appservices.serializers.SerializersCatalog;

public class T5SerializersCatalog {

    public static void main(String[] args) {

        ClientDTO c = new ClientDTO(5, "Pablo", "1990-01-01", "C/ Major", "600000000", "1234", "pass", "CODE", true);
        ClientSpanishDTO sc = new ClientSpanishDTO(6, "Jose", "1992-02-02", "C/ Minor", "600000001", "5678", "contraseña", "CODIGO", true);

        try {
            Serializer formatter =
                    SerializersCatalog.getInstance(Serializers.JSON_CLIENT);

            String jclient = formatter.serialize(c);
            System.out.println(jclient);

            ClientDTO clientDTO =
                    (ClientDTO) formatter.deserialize(jclient, ClientDTO.class);
            System.out.println(clientDTO);

            formatter =
                    SerializersCatalog.getInstance(Serializers.JSON_SP_CLIENT);

            String jsclient = formatter.serialize(sc);
            System.out.println(jsclient);

            ClientSpanishDTO spanishClient =
                    (ClientSpanishDTO) formatter.deserialize(jsclient, ClientSpanishDTO.class);
            System.out.println(spanishClient);

            formatter =
                    SerializersCatalog.getInstance(Serializers.XML_CLIENT);

            String xmlClient = formatter.serialize(c);
            System.out.println(xmlClient);

            clientDTO =
                    (ClientDTO) formatter.deserialize(xmlClient, ClientDTO.class);
            System.out.println(clientDTO);

            formatter =
                    SerializersCatalog.getInstance(Serializers.XML_SP_CLIENT);

            String xmlSpanishClient = formatter.serialize(sc);
            System.out.println(xmlSpanishClient);

            spanishClient =
                    (ClientSpanishDTO) formatter.deserialize(xmlSpanishClient, ClientSpanishDTO.class);
            System.out.println(spanishClient);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
