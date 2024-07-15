package co.edu.uptc.Gestor_de_rutas;

import co.edu.uptc.Gestor_de_rutas.controller.DijkstraAlgorithm;
import co.edu.uptc.Gestor_de_rutas.controller.GraphController;
import co.edu.uptc.Gestor_de_rutas.controller.PathToGeoJson;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GraphController controller = new GraphController();
        controller.createGraph();
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm();
        PathToGeoJson geoJsonMapper = new PathToGeoJson();
        Long startNodeId = 1016196839L;
        Long endNodeId = 7787924883L;
        System.out.println("Nodo de inicio: " + controller.getNodeById(startNodeId));
        System.out.println("Nodo de fin: " + controller.getNodeById(endNodeId));
        //double distance = dijkstraAlgorithm.dijkstra(startNodeId, endNodeId, controller.getGraph());
        geoJsonMapper.convertPathToGeoJson(dijkstraAlgorithm.getShortestPath(startNodeId, endNodeId, controller.getGraph()).getVertexList(), controller, "src/main/resources/templates/path.geojson");
        System.out.println("Camino más corto: " + dijkstraAlgorithm.getShortestPath(startNodeId, endNodeId, controller.getGraph()).getVertexList());
        System.out.println("Distancia total del camino más corto: " + dijkstraAlgorithm.longPath(dijkstraAlgorithm.getShortestPath(startNodeId, endNodeId, controller.getGraph()).getVertexList(), controller));
    }
}
