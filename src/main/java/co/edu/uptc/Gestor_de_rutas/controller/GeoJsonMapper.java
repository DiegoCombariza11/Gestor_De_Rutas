package co.edu.uptc.Gestor_de_rutas.controller;

import co.edu.uptc.Gestor_de_rutas.model.Node;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.json.JSONArray;
import org.json.JSONObject;

public class GeoJsonMapper {

    public JSONObject mapPathToGeoJson(GraphPath<String, DefaultWeightedEdge> path, GraphController graphController) {
        JSONObject geoJson = new JSONObject();
        geoJson.put("type", "FeatureCollection");
        JSONArray features = new JSONArray();

        // Agregar puntos para cada vértice en el camino
        for (String vertexId : path.getVertexList()) {
            Node node = graphController.getNodeById(Long.parseLong(vertexId));
            if (node != null) {
                JSONObject feature = new JSONObject();
                feature.put("type", "Feature");
                JSONObject geometry = new JSONObject();
                geometry.put("type", "Point");
                JSONArray coordinates = new JSONArray();
                coordinates.put(node.getX());
                coordinates.put(node.getY());
                geometry.put("coordinates", coordinates);
                feature.put("geometry", geometry);
                features.put(feature);
            }
        }

        // Agregar LineString para la ruta
        JSONObject lineFeature = new JSONObject();
        lineFeature.put("type", "Feature");
        JSONObject lineGeometry = new JSONObject();
        lineGeometry.put("type", "LineString");
        JSONArray lineCoordinates = new JSONArray();
        for (String vertexId : path.getVertexList()) {
            Node node = graphController.getNodeById(Long.parseLong(vertexId));
            if (node != null) {
                JSONArray point = new JSONArray();
                point.put(node.getX());
                point.put(node.getY());
                lineCoordinates.put(point);
            }
        }
        lineGeometry.put("coordinates", lineCoordinates);
        lineFeature.put("geometry", lineGeometry);
        features.put(lineFeature);

        geoJson.put("features", features);
        return geoJson;
    }
}