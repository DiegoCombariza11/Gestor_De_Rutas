package co.edu.uptc.Gestor_de_rutas.logic;

import co.edu.uptc.Gestor_de_rutas.model.*;
import co.edu.uptc.Gestor_de_rutas.persistence.InfoToJson;
import lombok.Getter;
import lombok.Setter;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AStarAlgorithm {

    private static AStarAlgorithm instance = null;
    InfoToJson infoToJson = new InfoToJson();

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

//    public GraphPath<Long, CustomEdge> findShortestPath(Long sourceVertex, Long targetVertex) {
//        AStarShortestPath<Long, CustomEdge> aStarShortestPath = new AStarShortestPath<>(graph, distanceBasedHeuristic);
//        GraphPath<Long, CustomEdge> shortestPathAStar = aStarShortestPath.getPath(sourceVertex, targetVertex);
//        setShortestPath(shortestPathAStar);
//        //getDistanceOfPath(shortestPathAStar);
//        shortestPathInfo.setDistance(getDistanceOfpathG(shortestPathAStar));
//        shortestPathInfo.setTime(getTimeOfPath(shortestPathAStar));
////        System.out.println(shortestPathInfo.getDistance()+ " distancia con el get");
////        System.out.println(shortestPathInfo.getTime()+ " Tiempo con el get");
//          return shortestPathAStar;
//    }
//
//
//    public List<GraphPath<Long, CustomEdge>> findTwoShortestPaths(Long sourceVertex, Long targetVertex) {
//
//    AStarShortestPath<Long, CustomEdge> aStarShortestPath = new AStarShortestPath<>(graph, distanceBasedHeuristic);
//    GraphPath<Long, CustomEdge> firstShortestPathAStar = aStarShortestPath.getPath(sourceVertex, targetVertex);
//    setShortestPath(firstShortestPathAStar);
//    shortestPathInfo.setDistance(getDistanceOfpathG(firstShortestPathAStar));
//    shortestPathInfo.setTime(getTimeOfPath(firstShortestPathAStar));
//        System.out.println("Distancia del primer camino: " + shortestPathInfo.getDistance());
//        System.out.println("Tiempo del primer camino: " + shortestPathInfo.getTime());
//
//    Set<Long> shortestPathNodes = new HashSet<>(firstShortestPathAStar.getVertexList());
//
//    AStarAdmissibleHeuristic<Long> secondPathHeuristic = new AStarAdmissibleHeuristic<>() {
//        @Override
//        public double getCostEstimate(Long sourceVertex, Long targetVertex) {
//            if (shortestPathNodes.contains(sourceVertex) || shortestPathNodes.contains(targetVertex)) {
//                return Double.MAX_VALUE;
//            }
//            Node sourceNode = graphController.getNodeById(sourceVertex);
//            Node targetNode = graphController.getNodeById(targetVertex);
//            if (sourceNode != null && targetNode != null) {
//                return haversine(sourceNode.getY(), sourceNode.getX(), targetNode.getY(), targetNode.getX());
//            }
//            return Double.MAX_VALUE;
//        }
//    };
//
//    AStarShortestPath<Long, CustomEdge> aStarSecondPath = new AStarShortestPath<>(graph, secondPathHeuristic);
//    GraphPath<Long, CustomEdge> secondShortestPathAStar = aStarSecondPath.getPath(sourceVertex, targetVertex);
//    setListOfShortestPaths(Arrays.asList(shortestPathInfo, new ShortestPathInfo(getDistanceOfpathG(secondShortestPathAStar), getTimeOfPath(secondShortestPathAStar))));
//
//    return Arrays.asList(firstShortestPathAStar, secondShortestPathAStar);
//}

    public List<GraphPath<Long, CustomEdge>> findTwoShortestPaths(Long sourceVertex, Long targetVertex) {
    AStarShortestPath<Long, CustomEdge> aStarShortestPath = new AStarShortestPath<>(graph, distanceBasedHeuristic);


    GraphPath<Long, CustomEdge> firstPath = aStarShortestPath.getPath(sourceVertex, targetVertex);
    ShortestPathInfo firstPathInfo = new ShortestPathInfo(getDistanceOfpathG(firstPath), getTimeOfPath(firstPath));
    //ShortestPathsAndInfo firstPathInfoComplete = new ShortestPathsAndInfo(firstPath, firstPathInfo);
//    System.out.println("first path: " + firstPath.getVertexList());
//    System.out.println("first path: " + firstPathInfo.getDistance() + "   " + firstPathInfo.getTime());


    Set<Long> firstPathNodes = new HashSet<>(firstPath.getVertexList());
    AStarAdmissibleHeuristic<Long> secondPathHeuristic = (source, target) -> firstPathNodes.contains(source) || firstPathNodes.contains(target) ? Double.MAX_VALUE : distanceBasedHeuristic.getCostEstimate(source, target);


    GraphPath<Long, CustomEdge> secondPath = new AStarShortestPath<>(graph, secondPathHeuristic).getPath(sourceVertex, targetVertex);
    ShortestPathInfo secondPathInfo = new ShortestPathInfo(getDistanceOfpathG(secondPath), getTimeOfPath(secondPath));
    //ShortestPathsAndInfo secondPathInfoComplete = new ShortestPathsAndInfo(secondPath, secondPathInfo);
    //System.out.println("second path: " + secondPath.getVertexList());
    //System.out.println("second path: " + secondPathInfo.getDistance() + "   " + secondPathInfo.getTime());
    List<ShortestPathInfo> shortestPathInfos = Arrays.asList(firstPathInfo, secondPathInfo);
    infoToJson.writeInfoToJson(shortestPathInfos, "src/main/resources/static/shortestPathInfo.json");

    return Arrays.asList(firstPath, secondPath);
}


    public double getDistanceOfpathG(GraphPath<Long, CustomEdge> path) {
        double totalDistance = 0.0;
        List<CustomEdge> edges = path.getEdgeList();
        for (CustomEdge edge : edges) {
            totalDistance += edge.getDistance();
        }
        totalDistance = Math.round(totalDistance * 100.0) / 100.0;
        return totalDistance;
    }

    public double getTimeOfPath(GraphPath<Long, CustomEdge> path) {
        double totalTime = 0.0;
        List<CustomEdge> edges = path.getEdgeList();
        for (CustomEdge edge : edges) {
            totalTime += edge.getTime();
        }
        totalTime = Math.round(totalTime * 100.0) / 100.0;

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


    public GraphPath<Long, CustomEdge> getPath() {
        if (shortestPath == null) {
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