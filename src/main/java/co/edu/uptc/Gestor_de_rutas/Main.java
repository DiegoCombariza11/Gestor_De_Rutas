package co.edu.uptc.Gestor_de_rutas;

import co.edu.uptc.Gestor_de_rutas.logic.DijkstraAlgorithm;
import co.edu.uptc.Gestor_de_rutas.logic.GraphController;
import co.edu.uptc.Gestor_de_rutas.persistence.PathToGeoJson;
import co.edu.uptc.Gestor_de_rutas.logic.RouteController;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;

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
        Long startNodeId = 2951857103L;
        Long endNodeId = 1016197420L;
//        List<Long> endNodes = new ArrayList<>();
//        List<GraphPath<Long, DefaultWeightedEdge>> c=dijkstraAlgorithm.getKShortestPaths(startNodeId, endNodeId, controller.getGraph(), 3);
//        for (int i = 0; i < c.size(); i++) {
//            geoJsonMapper.convertPathToGeoJson(routeController.getPath(c.get(i).getVertexList(), controller.getGraph(), controller), controller, "src/main/resources/templates/path"+(i+1)+".geojson");
////           endNodes.addAll(c.get(i).getVertexList());
//        }
        List<Long> endNodes = List.of(startNodeId, endNodeId);
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
        //System.out.println(routeController.getOsmId("UPTC, Sogamoso, Colombia"));
        System.out.println(dijkstraAlgorithm.longPath(dijkstraAlgorithm.getShortestPath(startNodeId, endNodeId, controller.getGraph()).getVertexList(), controller));
        System.out.println(dijkstraAlgorithm.getDijkstraTime(dijkstraAlgorithm.getShortestPath(startNodeId, endNodeId, controller.getGraph()).getVertexList(), controller));
    }
}
