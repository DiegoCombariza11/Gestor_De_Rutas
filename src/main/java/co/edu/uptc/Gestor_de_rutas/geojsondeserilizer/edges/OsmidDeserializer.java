package co.edu.uptc.Gestor_de_rutas.geojsondeserilizer.edges;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode; // Import the JsonNode class
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OsmidDeserializer extends JsonDeserializer<List<Long>> {
    @Override
    public List<Long> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        if (node.isArray()) {
            // Maneja el caso de un arreglo JSON
            List<Long> result = new ArrayList<>();
            node.forEach(element -> result.add(element.asLong()));
            return result;
        } else {
            // Maneja el caso de una cadena de texto
            String valueAsString = node.asText();
            if (valueAsString == null || valueAsString.isEmpty()) {
                return new ArrayList<>(); // Retorna una lista vacía si el valor es null o vacío
            }
            return Arrays.stream(valueAsString.split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }
    }
}