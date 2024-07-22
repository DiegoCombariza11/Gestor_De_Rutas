package co.edu.uptc.Gestor_de_rutas.logic;

import co.edu.uptc.Gestor_de_rutas.model.Edge;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.YenKShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DijkstraAlgorithm {
    public double dijkstra(Long startNodeId, Long endNodeId, Graph graph) {
        DijkstraShortestPath<Long, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        return dijkstraAlg.getPathWeight(startNodeId, endNodeId);
    }

    public DijkstraAlgorithm() {
    }

    public GraphPath<Long, DefaultWeightedEdge> getShortestPath(Long startNodeId, Long endNodeId, Graph graph) {
        DijkstraShortestPath<Long, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        longPath(dijkstraAlg.getPath(startNodeId, endNodeId).getVertexList(), new GraphController());
        return dijkstraAlg.getPath(startNodeId, endNodeId);
    }

    public double longPath(List<Long> path, GraphController controller) {
        double distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            distance += distanceBetweenNodes(path.get(i), path.get(i + 1), controller);
        }
        System.out.println("Distancia dijkstra: "+ distance);
        return distance;
    }

    public double distanceBetweenNodes(Long startNodeId, Long endNodeId, GraphController controller) {
        List<Edge> edgeList = controller.getEdges();
        for (Edge edge : edgeList) {
            if (edge.getU() == startNodeId && edge.getV() == endNodeId) {
                return edge.getLength();
            }
        }
        return 0;
    }

    public double getDijkstraTime(Long startNodeId, Long endNodeId, GraphController controller) {
       List<Edge> edgeList = controller.getEdges();
       double time = 0;
       for (Edge edge: edgeList){
           if (edge.getU() == startNodeId && edge.getV() == endNodeId){
               time += (edge.getLength()*60)/(edge.getMaxSpeed()*1000);
           }
       }
        return time;
     }

    public List<GraphPath<Long, DefaultWeightedEdge>> getKShortestPaths(Long startNodeId, Long endNodeId, Graph graph, int k) {
        YenKShortestPath<Long, DefaultWeightedEdge> yenAlg = new YenKShortestPath<>(graph);
        return yenAlg.getPaths(startNodeId, endNodeId, k);
    }
}
