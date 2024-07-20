package co.edu.uptc.Gestor_de_rutas;

import co.edu.uptc.Gestor_de_rutas.controller.AStarAlgorithm;
import co.edu.uptc.Gestor_de_rutas.controller.GraphController;
import co.edu.uptc.Gestor_de_rutas.controller.PathToGeoJson;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.StringTemplate.STR;

public class MainAStar {
    public static void main(String[] args) {

        GraphController graphController = new GraphController();
        //PathToGeoJson pathToGeoJson = new PathToGeoJson();

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



        Long startNodeId = 1016192858L;
        Long endNodeId = 1016193261L;
        String sourceVertexId = startNodeId.toString();
        String targetVertexId = endNodeId.toString();

        var pathS = aStarAlgorithm.findShortestPath(sourceVertexId, targetVertexId);
        var pathF = aStarAlgorithm.findFastestPath(sourceVertexId, targetVertexId);
        var paths = aStarAlgorithm.findTwoShortestPaths(sourceVertexId, targetVertexId);

//        if (pathS != null) {
//    System.out.println("Camino más corto encontrado: " + pathS);
//    List<Long> nodeIdList = pathS.getVertexList().stream()
//            .map(Long::parseLong)
//            .collect(Collectors.toList());
//
//
//
//    // Aquí se llama al método para generar el GeoJSON
//    pathToGeoJson.convertPathToGeoJson(nodeIdList, graphController, "paths.geojson");
//    System.out.println("Archivo GeoJSON generado correctamente.");
//} else {
//    System.out.println("No se encontró un camino.");
//}
//
//        if (pathF != null) {
//            System.out.println("Camino más rápido encontrado" + pathF);
//            List<Long> nodeidList2 = pathF.getVertexList().stream()
//                    .map(Long::parseLong)
//                    .collect(Collectors.toList());
//
//            pathToGeoJson.convertPathToGeoJson(nodeidList2, graphController, "pathF.geojson");
//            System.out.println("Archivo GeoJSON generado correctamente.");
//        }else{
//            System.out.println("No se encontró un camino.");
//        }



        if (paths != null) {
            System.out.println(STR."Caminos alternativos encontrados: \{paths}");
            List<List<Long>> allPaths = new ArrayList<>();
            for (GraphPath<String, DefaultWeightedEdge> path : paths) {
                List<Long> nodeIdList = path.getVertexList().stream()
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
                //System.out.println(path);
                allPaths.add(nodeIdList);
             }
            pathToGeoJson.convertPathsToGeoJson(allPaths, graphController, "paths.geojson");

            System.out.println("Archivo GeoJSON generados correctamente.");
        } else {
            System.out.println("No se encontraron caminos alternativos.");
        }




        /*
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

         */






    }}
