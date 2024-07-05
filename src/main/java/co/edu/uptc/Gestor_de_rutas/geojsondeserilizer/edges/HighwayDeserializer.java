package co.edu.uptc.Gestor_de_rutas.geojsondeserilizer.edges;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class HighwayDeserializer extends JsonDeserializer<String[]> {

    @Override
    public String[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        if (node.isTextual()) {
            // Si el campo highway es una cadena, conviértelo en un arreglo de una sola cadena
            return new String[]{node.asText()};
        } else if (node.isArray()) {
            // Si el campo highway es un arreglo, deserialízalo como tal
            String[] highways = new String[node.size()];
            for (int i = 0; i < node.size(); i++) {
                highways[i] = node.get(i).asText();
            }
            return highways;
        }
        // En caso de que el campo no sea ni una cadena ni un arreglo, retorna null o lanza una excepción
        return null; // O maneja este caso como consideres apropiado
    }
}