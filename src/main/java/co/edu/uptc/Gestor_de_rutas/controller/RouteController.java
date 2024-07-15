package co.edu.uptc.Gestor_de_rutas.controller;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import java.util.ArrayList;
import java.util.List;

public class RouteController {
    private DijkstraAlgorithm dijkstraAlgorithm;

    public RouteController() {
        this.dijkstraAlgorithm = new DijkstraAlgorithm();
    }

    public List<Long> setRoute(List<Long> endpoints, Graph<Long, DefaultEdge> graph, GraphController controller) {
        List<Long> route = new ArrayList<>();
        List<Long> remainingEndpoints = new ArrayList<>(endpoints);

        route.add(remainingEndpoints.remove(0));

        while (!remainingEndpoints.isEmpty()) {
            Long currentEndpoint = route.get(route.size() - 1);
            double shortestDistance = Double.MAX_VALUE;
            Long nextEndpoint = null;

            for (Long endpoint : remainingEndpoints) {
                double distance = dijkstraAlgorithm.longPath(
                        dijkstraAlgorithm.getShortestPath(currentEndpoint, endpoint, graph).getVertexList(),
                        controller
                );
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nextEndpoint = endpoint;
                }
            }

            if (nextEndpoint != null) {
                route.add(nextEndpoint);
                remainingEndpoints.remove(nextEndpoint);
            }
        }

        return route;
    }
    public List<Long> getPath(List<Long> endPoints, Graph graph,GraphController controller){
        List<Long> route= setRoute(endPoints, graph, controller);
        List<Long> path = new ArrayList<>();
        for (int i = 0; i < route.size() - 1; i++) {
            List<Long> aux = dijkstraAlgorithm.getShortestPath(route.get(i), route.get(i + 1), graph).getVertexList();
            path.addAll(aux);
        }
        return path;
    }
}
