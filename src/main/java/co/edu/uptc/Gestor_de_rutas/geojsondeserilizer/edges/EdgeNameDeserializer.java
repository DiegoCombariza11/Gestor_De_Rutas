package co.edu.uptc.Gestor_de_rutas.geojsondeserilizer.edges;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class EdgeNameDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        // Verifica si el nodo es un arreglo
        if (node.isArray()) {
            // Convierte el arreglo en una cadena, uniendo los elementos con comas
            StringBuilder sb = new StringBuilder();
            for (JsonNode element : node) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(element.asText());
            }
            return sb.toString();
        } else {
            // Si no es un arreglo, simplemente devuelve el valor como cadena
            return node.asText();
        }
    }
}