package co.edu.uptc.Gestor_de_rutas.service;

import co.edu.uptc.Gestor_de_rutas.logic.AStarAlgorithm;
import co.edu.uptc.Gestor_de_rutas.logic.DijkstraAlgorithm;
import co.edu.uptc.Gestor_de_rutas.logic.GraphController;
import co.edu.uptc.Gestor_de_rutas.model.CustomEdge;
import co.edu.uptc.Gestor_de_rutas.persistence.PathToGeoJson;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PathService {

    private final PathToGeoJson pathToGeoJson;
    private GraphController graphController;
    private final DijkstraAlgorithm dijkstraAlgorithm;
    private AStarAlgorithm aStarAlgorithm;
    private final Long startNodeId = 2951857103L;
    @Setter
    private String endNodeID;

    public void shortestPaths() {
        this.graphController = new GraphController();
        var shortestPaths = dijkstraAlgorithm.getKShortestPaths(startNodeId, Long.parseLong(endNodeID), graphController.getGraph(), 1);
        if (shortestPaths != null) {
            List<Long> fastestPathsNodes = shortestPaths.get(0).getVertexList();
            pathToGeoJson.convertPathToGeoJson(fastestPathsNodes, graphController, "src/main/resources/static/shortestPath.geojson");

        }
    }


//

    public void shortestPathAStar() {
        this.graphController = new GraphController();
        this.aStarAlgorithm =  AStarAlgorithm.getInstance(graphController);
        GraphPath<Long, CustomEdge> shortestPaths = aStarAlgorithm.findShortestPath(startNodeId, Long.valueOf(endNodeID));
        if (shortestPaths != null) {
            List<Long> shortestPathsNodes = new ArrayList<>(shortestPaths.getVertexList());
            System.out.println("ShortestPathAStar hecho");
            pathToGeoJson.convertPathToGeoJson(shortestPathsNodes, graphController, "src/main/resources/static/shortestPathAStar.geojson");
        }
    }

    public void shortestPathsAStar() {
        this.graphController = new GraphController();
        this.aStarAlgorithm = AStarAlgorithm.getInstance(graphController);
        List<GraphPath<Long, CustomEdge>> shortestPaths = aStarAlgorithm.findTwoShortestPaths(startNodeId, Long.valueOf(endNodeID));
        if (shortestPaths != null) {
            List<Long> shortestPathsNodes = new ArrayList<>(shortestPaths.get(0).getVertexList());
            List<Long> shortestPathsNodes2 = new ArrayList<>(shortestPaths.get(1).getVertexList());
            pathToGeoJson.convertPathToGeoJson(shortestPathsNodes, graphController, "src/main/resources/static/shortestPathAStar1.geojson");
            pathToGeoJson.convertPathToGeoJson(shortestPathsNodes2, graphController, "src/main/resources/static/shortestPathAStar2.geojson");
        }
    }



    /*

    public void fastestPath() {
        this.graphController = new GraphController();
        Graph<Long, DefaultWeightedEdge> convertedGraph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        graphController.getGraph().vertexSet().forEach(vertex -> convertedGraph.addVertex(vertex));
        graphController.getGraph().edgeSet().forEach(edge -> {
            Long source = graphController.getGraph().getEdgeSource(edge);
            Long target = graphController.getGraph().getEdgeTarget(edge);
            DefaultWeightedEdge convertedEdge = convertedGraph.addEdge(source, target);
            if (convertedEdge != null) {
                double distance = graphController.getGraph().getEdgeWeight(edge);
                double maxSpeed = graphController.getMaxSpeedForEdge(source.toString(), target.toString());
                double travelTime = distance / maxSpeed;
                convertedGraph.setEdgeWeight(convertedEdge, travelTime);
            }
        });

        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(convertedGraph, graphController);
        System.out.println(endNodeID + "Destino de A*");
        GraphPath<Long, DefaultWeightedEdge> fastestPaths = aStarAlgorithm.findFastestPath(startNodeId, Long.valueOf(endNodeID));
        System.out.println("Path con A* generado");
        if (fastestPaths != null) {

           List<Long> fastestPathsNodes = new ArrayList<>(fastestPaths.getVertexList());
            System.out.println("Convirtiendo a geojsonn A*");
            pathToGeoJson.convertPathToGeoJson(fastestPathsNodes, graphController, "src/main/resources/static/fastestPath.geojson");
            System.out.println("Archivo convertido a Geojson");
        }
    }

     */

}
