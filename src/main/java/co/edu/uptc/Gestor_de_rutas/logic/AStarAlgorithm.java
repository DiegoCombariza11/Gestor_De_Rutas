package co.edu.uptc.Gestor_de_rutas.logic;

import co.edu.uptc.Gestor_de_rutas.controller.PathController;
import co.edu.uptc.Gestor_de_rutas.model.CustomEdge;
import co.edu.uptc.Gestor_de_rutas.model.Edge;
import co.edu.uptc.Gestor_de_rutas.model.Node;
import co.edu.uptc.Gestor_de_rutas.model.ShortestPathInfo;
import lombok.Getter;
import lombok.Setter;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AStarAlgorithm {

    private static AStarAlgorithm instance = null;

    @Getter
    private ShortestPathInfo shortestPathInfo = new ShortestPathInfo();

    private Graph<Long, CustomEdge> graph;
    @Setter
    private GraphPath<Long, CustomEdge> shortestPath;
    private GraphController graphController;
    private AStarAdmissibleHeuristic<Long> distanceBasedHeuristic;
    @Getter
    @Setter
    private double distance;
    @Getter
    @Setter
    private double time;
    private PathController pathController;


    public AStarAlgorithm(GraphController graphController) {
    this.graphController = graphController;
    this.graph = graphController.getShortesGraph();
    this.distanceBasedHeuristic = createHeuristic();
}
public static AStarAlgorithm getInstance(GraphController graphController) {
    if (instance == null) {
        instance = new AStarAlgorithm(graphController);
    }
    return instance;
}

    public void setShortestPath(GraphPath<Long, CustomEdge> shortestPath) {
        this.shortestPath = shortestPath;
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

    public GraphPath<Long, CustomEdge> findShortestPath(Long sourceVertex, Long targetVertex) {
        AStarShortestPath<Long, CustomEdge> aStarShortestPath = new AStarShortestPath<>(graph, distanceBasedHeuristic);
        GraphPath<Long, CustomEdge> shortestPathAStar = aStarShortestPath.getPath(sourceVertex, targetVertex);
        setShortestPath(shortestPathAStar);
        //getDistanceOfPath(shortestPathAStar);
        shortestPathInfo.setDistance(getDistanceOfpathG(shortestPathAStar));
        shortestPathInfo.setTime(getTimeOfPath(shortestPathAStar));
//        System.out.println(shortestPathInfo.getDistance()+ " distancia con el get");
//        System.out.println(shortestPathInfo.getTime()+ " Tiempo con el get");
          return shortestPathAStar;
    }


    public List<GraphPath<Long, CustomEdge>> findTwoShortestPaths(Long sourceVertex, Long targetVertex) {

    AStarShortestPath<Long, CustomEdge> aStarShortestPath = new AStarShortestPath<>(graph, distanceBasedHeuristic);
    GraphPath<Long, CustomEdge> shortestPathAStar = aStarShortestPath.getPath(sourceVertex, targetVertex);
    setShortestPath(shortestPathAStar);
    shortestPathInfo.setDistance(getDistanceOfpathG(shortestPathAStar));
    shortestPathInfo.setTime(getTimeOfPath(shortestPathAStar));

    Set<Long> shortestPathNodes = new HashSet<>(shortestPathAStar.getVertexList());

    AStarAdmissibleHeuristic<Long> secondPathHeuristic = new AStarAdmissibleHeuristic<>() {
        @Override
        public double getCostEstimate(Long sourceVertex, Long targetVertex) {
            if (shortestPathNodes.contains(sourceVertex) || shortestPathNodes.contains(targetVertex)) {
                return Double.MAX_VALUE;
            }
            Node sourceNode = graphController.getNodeById(sourceVertex);
            Node targetNode = graphController.getNodeById(targetVertex);
            if (sourceNode != null && targetNode != null) {
                return haversine(sourceNode.getY(), sourceNode.getX(), targetNode.getY(), targetNode.getX());
            }
            return Double.MAX_VALUE;
        }
    };

    AStarShortestPath<Long, CustomEdge> aStarSecondPath = new AStarShortestPath<>(graph, secondPathHeuristic);
    GraphPath<Long, CustomEdge> secondShortestPathAStar = aStarSecondPath.getPath(sourceVertex, targetVertex);

    // Return the two paths
    return Arrays.asList(shortestPathAStar, secondShortestPathAStar);
}


    public double getDistanceOfpathG(GraphPath<Long, CustomEdge> path){
        double totalDistance = 0.0;
        List<CustomEdge> edges = path.getEdgeList();
        for (CustomEdge edge : edges) {
            totalDistance += edge.getDistance();
        }
        totalDistance = Math.round(totalDistance * 100.0) / 100.0;
        System.out.println("Distancia con A* (el más corto): " + totalDistance + " m");
        return totalDistance;
    }

    public double getTimeOfPath(GraphPath<Long, CustomEdge> path) {
        double totalTime = 0.0;
        List<CustomEdge> edges = path.getEdgeList();
        for (CustomEdge edge : edges) {
            totalTime += edge.getTime();
        }
        totalTime = Math.round(totalTime * 100.0) / 100.0;
        System.out.println("Tiempo del más corto (a*): " + totalTime + " minutos");

    return totalTime;
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


    public GraphPath<Long, CustomEdge> getPath(){
        if (shortestPath == null){
            System.out.println("GraphPath es null");
        }
        return shortestPath;
    }




















//    public double getDistanceOfPath(GraphPath<Long, CustomEdge> path) {
//        double totalDistance = 0.0;
//        List<CustomEdge> edges = path.getEdgeList();
//        for (CustomEdge edge : edges) {
//            Long sourceVertex = graph.getEdgeSource(edge);
//            Long targetVertex = graph.getEdgeTarget(edge);
//            Node sourceNode = graphController.getNodeById(sourceVertex);
//            Node targetNode = graphController.getNodeById(targetVertex);
//            if (sourceNode != null && targetNode != null) {
//                totalDistance += haversine(sourceNode.getY(), sourceNode.getX(), targetNode.getY(), targetNode.getX());
//            }
//        }
//        //System.out.println("Distancia: " + totalDistance + " m");
//        return totalDistance;
//    }
}