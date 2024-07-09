package co.edu.uptc.Gestor_de_rutas.persistence;

import co.edu.uptc.Gestor_de_rutas.model.Edge;
import co.edu.uptc.Gestor_de_rutas.model.Node;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileManagement {
    public static Map<Long, Node> readNodes(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(filePath));
        JsonNode features = rootNode.get("features");

        Map<Long, Node> nodes = new HashMap<>();
        for (JsonNode feature : features) {
            JsonNode properties = feature.get("properties");
            long osmid = properties.get("osmid").asLong();
            double y = properties.get("y").asDouble();
            double x = properties.get("x").asDouble();
//
//            Node node = new Node(osmid, y, x);
//            nodes.put(osmid, node);
        }
        return nodes;
    }

    public static Map<Long, Edge> readEdges(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(filePath));
        JsonNode features = rootNode.get("features");

        Map<Long, Edge> edges = new HashMap<>();
        for (JsonNode feature : features) {
            JsonNode properties = feature.get("properties");
            long u = properties.get("u").asLong();
            long v = properties.get("v").asLong();
            double length = properties.get("length").asDouble();
//
//            Edge edge = new Edge(u, v, length);
//            edges.put(u, edge);
        }
        return edges;
    }
}
