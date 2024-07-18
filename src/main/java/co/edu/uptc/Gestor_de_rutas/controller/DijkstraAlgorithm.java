package co.edu.uptc.Gestor_de_rutas.controller;

import co.edu.uptc.Gestor_de_rutas.model.Edge;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DijkstraAlgorithm {
    public double dijkstra(Long startNodeId, Long endNodeId, Graph graph) {
        DijkstraShortestPath<Long, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        return dijkstraAlg.getPathWeight(startNodeId, endNodeId);
    }
    public GraphPath<Long, DefaultWeightedEdge> getShortestPath(Long startNodeId, Long endNodeId,Graph graph) {
        DijkstraShortestPath<Long, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        return dijkstraAlg.getPath(startNodeId, endNodeId);
    }
    public double longPath(List<Long> path, GraphController controller){
        double distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            distance += distanceBetweenNodes(path.get(i),path.get(i + 1), controller);
        }
        return distance;
    }
    public double distanceBetweenNodes(Long startNodeId, Long endNodeId, GraphController controller) {
        List<Edge> edgeList = controller.getEdges();
        for (Edge edge : edgeList) {
            if(edge.getU()==startNodeId && edge.getV()==endNodeId){
                return edge.getLength();
            }
        }
        return 0;
    }
}
