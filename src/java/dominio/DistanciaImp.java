package dominio;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class DistanciaImp {

    public static double obtenerDistancia(String cpOrigen, String cpDestino) {

        String url = "http://sublimas.com.mx:8080/calculadora/api/envios/distancia/"
                   + cpOrigen + "," + cpDestino;

        Client client = ClientBuilder.newClient();
        String response = client
                .target(url)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        JsonObject json = JsonParser.parseString(response).getAsJsonObject();

        return json.get("distanciaKM").getAsDouble();
    }
}
