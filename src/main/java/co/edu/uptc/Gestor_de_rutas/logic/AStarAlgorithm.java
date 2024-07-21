package co.edu.uptc.Gestor_de_rutas.logic;

import co.edu.uptc.Gestor_de_rutas.model.Edge;
import co.edu.uptc.Gestor_de_rutas.model.Node;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Component;

@Component
public class AStarAlgorithm {

    private Graph<Long, DefaultEdge> graph;
    private GraphController graphController;
    private AStarAdmissibleHeuristic<Long> distanceBasedHeuristic;

    public AStarAlgorithm(GraphController graphController) {
    this.graphController = graphController;
    this.graph = graphController.getShortesGraph();
    this.distanceBasedHeuristic = createHeuristic();
}

    private AStarAdmissibleHeuristic<Long> createHeuristic() {
        return new AStarAdmissibleHeuristic<>() {
            @Override
            public double getCostEstimate(Long sourceVertex, Long targetVertex) {
                Node sourceNode = graphController.getNodeById(sourceVertex);
                Node targetNode = graphController.getNodeById(targetVertex);
                if (sourceNode != null && targetNode != null) {
                    return haversine(sourceNode.getY(), sourceNode.getX(), targetNode.getY(), targetNode.getX());
                }
                return Double.MAX_VALUE;
            }
        };
    }

    public GraphPath<Long, DefaultEdge> findShortestPath(Long sourceVertex, Long targetVertex) {
        AStarShortestPath<Long, DefaultEdge> aStarShortestPath = new AStarShortestPath<>(graph, distanceBasedHeuristic);
        return aStarShortestPath.getPath(sourceVertex, targetVertex);
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;  // Distance in km
    }
}