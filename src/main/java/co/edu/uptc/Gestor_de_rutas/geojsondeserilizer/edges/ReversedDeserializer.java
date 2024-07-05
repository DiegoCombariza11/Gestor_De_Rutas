package co.edu.uptc.Gestor_de_rutas.geojsondeserilizer.edges;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReversedDeserializer extends JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        if (node.isArray()) {
            List<Boolean> list = new ArrayList<>();
            node.forEach(jsonNode -> list.add(jsonNode.asBoolean()));
            return list;
        } else if (node.isBoolean()) {
            return node.asBoolean();
        } else {
            // Handle string "True"/"False" as boolean
            String textValue = node.asText();
            return "True".equalsIgnoreCase(textValue) || "False".equalsIgnoreCase(textValue) ? Boolean.parseBoolean(textValue.toLowerCase()) : null;
        }
    }
}