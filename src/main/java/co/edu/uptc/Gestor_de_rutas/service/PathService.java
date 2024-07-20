package co.edu.uptc.Gestor_de_rutas.service;

import co.edu.uptc.Gestor_de_rutas.logic.AStarAlgorithm;
import co.edu.uptc.Gestor_de_rutas.logic.DijkstraAlgorithm;
import co.edu.uptc.Gestor_de_rutas.logic.GraphController;
import co.edu.uptc.Gestor_de_rutas.persistence.PathToGeoJson;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PathService {

    private final PathToGeoJson pathToGeoJson;
    private GraphController graphController;
    private final DijkstraAlgorithm dijkstraAlgorithm;
    private AStarAlgorithm aStarAlgorithm;
    private final String startNodeId = "2951857103";
    @Setter
    private String endNodeID;

    public void shortestPaths() {
        this.graphController = new GraphController();
        var shortestPaths = dijkstraAlgorithm.getKShortestPaths(Long.valueOf(startNodeId), Long.parseLong(endNodeID), graphController.getGraph(), 1);
        if (shortestPaths != null) {
            List<Long> fastestPathsNodes = shortestPaths.get(0).getVertexList();
            pathToGeoJson.convertPathToGeoJson(fastestPathsNodes, graphController, "src/main/resources/static/shortestPath.geojson");

        }
    }

    public void fastestPaths() {
        this.graphController = new GraphController();
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
        var fastestPaths = aStarAlgorithm.findFastestPath(startNodeId, endNodeID);

        if (fastestPaths != null) {

            List<Long> fastestPathsNodes = fastestPaths.getVertexList().stream()
                    .map(Long::parseLong)
                    .toList();

            pathToGeoJson.convertPathToGeoJson(fastestPathsNodes, graphController, "src/main/resources/static/fastestPath.geojson");

        }

    }
}
