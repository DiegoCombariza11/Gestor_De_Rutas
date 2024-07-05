package co.edu.uptc.Gestor_de_rutas.controller;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.HashMap;
import java.util.Map;

public class DijkstraAlgorithm {
    public double dijkstra(Long startNodeId, Long endNodeId, Graph graph) {
        DijkstraShortestPath<Long, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        return dijkstraAlg.getPathWeight(startNodeId, endNodeId);
    }
}
