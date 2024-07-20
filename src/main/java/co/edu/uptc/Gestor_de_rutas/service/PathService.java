package co.edu.uptc.Gestor_de_rutas.service;

import co.edu.uptc.Gestor_de_rutas.logic.AStarAlgorithm;
import co.edu.uptc.Gestor_de_rutas.logic.DijkstraAlgorithm;
import co.edu.uptc.Gestor_de_rutas.logic.GraphController;
import co.edu.uptc.Gestor_de_rutas.persistence.PathToGeoJson;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PathService {

    private final PathToGeoJson pathToGeoJson;
    private final GraphController graphController;
    private final DijkstraAlgorithm dijkstraAlgorithm;
    private final AStarAlgorithm aStarAlgorithm;
    private final String startNodeId = "2951857103";
    @Setter
    private String endNodeID;

    public void shortestPaths() {
        var shortestPaths = dijkstraAlgorithm.getKShortestPaths(Long.valueOf(startNodeId), 0L, graphController.getGraph(), 1);
    }

    public void fastestPaths() {
        var fastestPaths = aStarAlgorithm.findFastestPath(startNodeId, endNodeID);

        if (fastestPaths != null) {

            List<Long> fastestPathsNodes = fastestPaths.getVertexList().stream()
                    .map(Long::parseLong)
                    .toList();

            pathToGeoJson.convertPathToGeoJson(fastestPathsNodes, graphController, "src/main/resources/static/fastestPath.geojson");

        }

    }
}
