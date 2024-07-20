package co.edu.uptc.Gestor_de_rutas.logic;

import co.edu.uptc.Gestor_de_rutas.model.Edge;
import co.edu.uptc.Gestor_de_rutas.model.Node;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AStarAlgorithm {

    private Graph<String, DefaultWeightedEdge> graph;
    private GraphController graphController;
    public static final String sourceUPTC = "2951857103";

    public AStarAlgorithm(Graph<String, DefaultWeightedEdge> graph, GraphController graphController) {
        this.graph = graph;
        this.graphController = graphController;
    }

    public AStarAlgorithm() {
    }

    private AStarAdmissibleHeuristic<String> heuristic = new AStarAdmissibleHeuristic<>() {
        @Override
        public double getCostEstimate(String sourceVertex, String targetVertex) {
            Node sourceNode = graphController.getNodes().stream()
                    .filter(n -> n.getOsmid() == Long.parseLong(sourceVertex))
                    .findFirst()
                    .orElse(null);
            Node targetNode = graphController.getNodes().stream()
                    .filter(n -> n.getOsmid() == Long.parseLong(targetVertex))
                    .findFirst()
                    .orElse(null);
            if (sourceNode != null && targetNode != null) {
                return haversine(sourceNode.getY(), sourceNode.getX(), targetNode.getY(), targetNode.getX());
            }
            return Double.MAX_VALUE;
        }
    };

   public double getMaxSpeedForEdge(String sourceVertex, String targetVertex) {
    long sourceVertexLong = Long.parseLong(sourceVertex);
    long targetVertexLong = Long.parseLong(targetVertex);
    List<Edge> edgeList = graphController.getEdges();
    for (Edge edge : edgeList) {
        if ((edge.getU() == sourceVertexLong && edge.getV() == targetVertexLong)
                || (edge.getV() == sourceVertexLong && edge.getU() == targetVertexLong)) {
            return edge.getMaxSpeed();
        }
    }
    return Double.MAX_VALUE;
}

    private AStarAdmissibleHeuristic<String> timeBasedHeuristic = new AStarAdmissibleHeuristic<>() {
    @Override
    public double getCostEstimate(String sourceVertex, String targetVertex) {
        Node sourceNode = graphController.getNodeById(Long.parseLong(sourceVertex));
        Node targetNode = graphController.getNodeById(Long.parseLong(targetVertex));
        if (sourceNode != null && targetNode != null) {
            double distance = haversine(sourceNode.getY(), sourceNode.getX(), targetNode.getY(), targetNode.getX());
            double maxSpeed = getMaxSpeedForEdge(sourceVertex, targetVertex);
            double estimatedTravelTime = distance / maxSpeed;
            return estimatedTravelTime;
        }
        return Double.MAX_VALUE;
    }
};

public GraphPath<String, DefaultWeightedEdge> findFastestPath(String sourceVertex, String targetVertex) {
    AStarShortestPath<String, DefaultWeightedEdge> aStarShortestPath = new AStarShortestPath<>(graph, timeBasedHeuristic);
    return aStarShortestPath.getPath(sourceVertex, targetVertex);
}





    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public GraphPath<String, DefaultWeightedEdge> findShortestPath(String sourceVertex, String targetVertex) {
        AStarShortestPath<String, DefaultWeightedEdge> aStarShortestPath = new AStarShortestPath<>(graph, heuristic);
        return aStarShortestPath.getPath(sourceVertex, targetVertex);
    }

    public List<GraphPath<String, DefaultWeightedEdge>> findTwoShortestPaths(String sourceVertex, String targetVertex) {
        List<GraphPath<String, DefaultWeightedEdge>> paths = new ArrayList<>();

        // Encuentra el primer camino más corto
        AStarShortestPath<String, DefaultWeightedEdge> aStar = new AStarShortestPath<>(graph,
                heuristic);
        GraphPath<String, DefaultWeightedEdge> firstPath = aStar.getPath(sourceVertex, targetVertex);
        if (firstPath != null) {
            paths.add(firstPath);

            // Excluir temporalmente las aristas del primer camino
            Set<DefaultWeightedEdge> excludedEdges = firstPath.getEdgeList().stream().collect(Collectors.toSet());
            excludedEdges.forEach(edge -> graph.setEdgeWeight(edge, Double.POSITIVE_INFINITY));

            // Buscar el segundo camino más corto
            GraphPath<String, DefaultWeightedEdge> secondPath = aStar.getPath(sourceVertex, targetVertex);

            // Restaurar los pesos de las aristas excluidas
            excludedEdges.forEach(edge -> graph.setEdgeWeight(edge, graph.getEdgeWeight(edge)));

            if (secondPath != null && !secondPath.equals(firstPath)) {
                paths.add(secondPath);
            }
        }

        return paths;
    }


    public static double calculateDistance(List<List<Double>> coordinates) {
        double totalDistance = 0.0;
        final int R = 6371;

        for (int i = 0; i < coordinates.size() - 1; i++) {
            List<Double> start = coordinates.get(i);
            List<Double> end = coordinates.get(i + 1);

            double latDistance = Math.toRadians(end.get(1) - start.get(1));
            double lonDistance = Math.toRadians(end.get(0) - start.get(0));
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(start.get(1))) * Math.cos(Math.toRadians(end.get(1)))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = R * c;

            totalDistance += distance;
        }

        return totalDistance;
    }
}
