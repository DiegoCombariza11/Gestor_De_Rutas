package co.edu.uptc.Gestor_de_rutas.controller;

import co.edu.uptc.Gestor_de_rutas.model.Node;
import co.edu.uptc.Gestor_de_rutas.controller.GraphController;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PathToGeoJson {

    public void convertPathsToGeoJson(List<List<Long>> paths, GraphController graphController, String outputPath) {
    JSONObject geoJson = new JSONObject();
    JSONArray features = new JSONArray();
    geoJson.put("type", "FeatureCollection");

    int pathId = 0;
    for (List<Long> path : paths) {
        // puntitos
        for (int i = 0; i < path.size(); i++) {
            Long nodeId = path.get(i);
            Node node = graphController.getNodes().stream()
                    .filter(n -> n.getOsmid() == nodeId)
                    .findFirst()
                    .orElse(null);

            if (node != null) {
                JSONObject feature = createFeature(node, pathId++);
                features.put(feature);
            }
        }

        // lineas que unen los puntitos
        List<Node> nodesInPath = path.stream()
                .map(nodeId -> graphController.getNodes().stream()
                        .filter(n -> n.getOsmid() == nodeId)
                        .findFirst()
                        .orElse(null))
                .collect(Collectors.toList());
        JSONObject lineFeature = createLineFeature(nodesInPath);
        features.put(lineFeature);
    }

    geoJson.put("features", features);
    writeGeoJsonToFile(geoJson, outputPath);
}



private String createGeoJsonFromNodeIds(List<Long> nodeIdList, GraphController graphController) {
    // Implementar la lógica para convertir la lista de ID de nodos en una cadena GeoJSON
    // Esta es una implementación ficticia y debe ser reemplazada por la lógica real
    return "{ \"type\": \"FeatureCollection\", \"features\": [] }";
}


    public void convertPathToGeoJson(List<Long> path, GraphController graphController, String outputPath) {
        JSONObject geoJson = new JSONObject();

        JSONArray features = new JSONArray();
        geoJson.put("type", "FeatureCollection");

        // puntitos
        for (int i = 0; i < path.size(); i++) {
            Long nodeId = path.get(i);
            Node node = graphController.getNodes().stream()
                    .filter(n -> n.getOsmid() == nodeId)
                    .findFirst()
                    .orElse(null);

            if (node != null) {
                JSONObject feature = createFeature(node, i);
                features.put(feature);
            }
        }

      // lineas que unen los puntitos
        List<Node> nodesInPath = path.stream()
                .map(nodeId -> graphController.getNodes().stream()
                        .filter(n -> n.getOsmid() == (nodeId))
                        .findFirst()
                        .orElse(null))
                .collect(Collectors.toList());
        JSONObject lineFeature = createLineFeature(nodesInPath);
        features.put(lineFeature);

        geoJson.put("features", features);

        writeGeoJsonToFile(geoJson, outputPath);
    }

    private JSONObject createFeature(Node node, int id) {
    JSONObject feature = new JSONObject();
    feature.put("type", "Feature");
    feature.put("id", id);

    JSONObject geometry = new JSONObject();
    geometry.put("type", "Point");
    JSONArray coordinates = new JSONArray();
    coordinates.put(node.getX());
    coordinates.put(node.getY());
    geometry.put("coordinates", coordinates);

    JSONObject properties = new JSONObject();
    properties.put("osmid", node.getOsmid());

    feature.put("geometry", geometry);
    feature.put("properties", properties);

    return feature;
}

    private JSONObject createLineFeature(List<Node> nodes) {
        JSONObject lineFeature = new JSONObject();
        lineFeature.put("type", "Feature");

        JSONObject geometry = new JSONObject();
        geometry.put("type", "LineString");
        JSONArray coordinates = new JSONArray();

        for (Node node : nodes) {
            JSONArray point = new JSONArray();
            point.put(node.getX());
            point.put(node.getY());
            coordinates.put(point);
        }

        geometry.put("coordinates", coordinates);
        lineFeature.put("geometry", geometry);
        lineFeature.put("properties", new JSONObject());

        return lineFeature;
    }


    private void writeGeoJsonToFile(JSONObject geoJson, String outputPath) {
        try (FileWriter file = new FileWriter(outputPath)) {
            file.write(geoJson.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}