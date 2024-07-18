package co.edu.uptc.Gestor_de_rutas;

import co.edu.uptc.Gestor_de_rutas.controller.AStarAlgorithm;
import co.edu.uptc.Gestor_de_rutas.controller.GraphController;
//import co.edu.uptc.Gestor_de_rutas.controller.PathToGeoJson;
import co.edu.uptc.Gestor_de_rutas.controller.PathToGeoJson;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.List;
import java.util.stream.Collectors;

public class MainAStar {
    public static void main(String[] args) {

        GraphController graphController = new GraphController();
        PathToGeoJson pathToGeoJson = new PathToGeoJson();

        graphController.createGraph();

        AStarAlgorithm aStarAlgorithm = null;


        //System.out.println(graphController.getGraph().toString());


        Graph<String, DefaultWeightedEdge> convertedGraph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        graphController.getGraph().vertexSet().forEach(vertex -> convertedGraph.addVertex(vertex.toString()));
        graphController.getGraph().edgeSet().forEach(edge -> {
            String source = graphController.getGraph().getEdgeSource(edge).toString();
            String target = graphController.getGraph().getEdgeTarget(edge).toString();
            DefaultWeightedEdge convertedEdge = convertedGraph.addEdge(source, target);
            if (convertedEdge != null) {
                convertedGraph.setEdgeWeight(convertedEdge, graphController.getGraph().getEdgeWeight(edge));
            }
        });

        aStarAlgorithm = new AStarAlgorithm(convertedGraph, graphController);



        Long startNodeId = 1016196839L;
        Long endNodeId = 7787924883L;
        String sourceVertexId = startNodeId.toString();
        String targetVertexId = endNodeId.toString();

        var path = aStarAlgorithm.findShortestPath(sourceVertexId, targetVertexId);


        if (path != null) {
            System.out.println("Camino encontrado: " + path);
            List<Long> nodeIdList = path.getVertexList().stream()
                    .map(vertex -> Long.parseLong(vertex))
                    .collect(Collectors.toList());

            double totalDistance = aStarAlgorithm.calculateDistance((List<List<Double>>) path);
            System.out.println("Distancia total: " + totalDistance + " m");

            pathToGeoJson.convertPathToGeoJson(nodeIdList, graphController, "path.geojson");
        } else {
            System.out.println("No se encontró un camino.");
        }


    }}
