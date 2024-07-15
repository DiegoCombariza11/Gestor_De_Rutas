package co.edu.uptc.Gestor_de_rutas;

import co.edu.uptc.Gestor_de_rutas.controller.DijkstraAlgorithm;
import co.edu.uptc.Gestor_de_rutas.controller.GraphController;
import co.edu.uptc.Gestor_de_rutas.controller.PathToGeoJson;
import co.edu.uptc.Gestor_de_rutas.controller.RouteController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        GraphController controller = new GraphController();
        controller.createGraph();
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm();
        PathToGeoJson geoJsonMapper = new PathToGeoJson();
        RouteController routeController = new RouteController();
        Long startNodeId = 1016196839L;
        Long endNodeId = 7787924883L;
        List<Long> endNodes = List.of(1016183269L,startNodeId, endNodeId,1016185698L);
//        List<Long> route= routeController.setRoute(endNodes, controller.getGraph(), controller);
//        //double distance = dijkstraAlgorithm.dijkstra(startNodeId, endNodeId, controller.getGraph());
//        System.out.println("Ruta: " + route);
//        List<Long> path = new ArrayList<>();
//        for (int i = 0; i < route.size() - 1; i++) {
//            System.out.println("Ruta: " + route.get(i) + " -> " + route.get(i + 1));
//            List<Long> aux = dijkstraAlgorithm.getShortestPath(route.get(i), route.get(i + 1), controller.getGraph()).getVertexList();
//            System.out.println(aux+"\n");
//            path.addAll(aux);
//        }
//        System.out.println("Ruta: " + path);
        geoJsonMapper.convertPathToGeoJson(routeController.getPath(endNodes, controller.getGraph(), controller), controller, "src/main/resources/templates/path.geojson");
    }
}
