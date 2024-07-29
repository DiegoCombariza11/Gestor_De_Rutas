package co.edu.uptc.Gestor_de_rutas.logic;

import co.edu.uptc.Gestor_de_rutas.model.Edge;
import co.edu.uptc.Gestor_de_rutas.model.ShortestPathInfo;
import co.edu.uptc.Gestor_de_rutas.persistence.InfoToJson;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.YenKShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DijkstraAlgorithm {
    private InfoToJson infoToJson ;
    public double dijkstraWeight(Long startNodeId, Long endNodeId, Graph graph) {
        DijkstraShortestPath<Long, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        return dijkstraAlg.getPathWeight(startNodeId, endNodeId);
    }

    public DijkstraAlgorithm() {
        this.infoToJson = new InfoToJson();
    }

    public GraphPath<Long, DefaultWeightedEdge> getShortestPath(Long startNodeId, Long endNodeId, Graph graph) {
        DijkstraShortestPath<Long, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        return dijkstraAlg.getPath(startNodeId, endNodeId);
    }

    public double longPath(List<Long> path, GraphController controller) {
        double distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            distance += distanceBetweenNodes(path.get(i), path.get(i + 1), controller);
        }
        distance= Math.round(distance * 100.0) / 100.0;
        //System.out.println("Distancia dijkstra: "+ distance);
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

    public double getDijkstraTime(List<Long> path, GraphController controller) {
        double totalTime = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Long startNodeId = path.get(i);
            Long endNodeId = path.get(i + 1);
            double distance = distanceBetweenNodes(startNodeId, endNodeId, controller);
            double speed = speedBetweenNodes(startNodeId, endNodeId, controller);
            //System.out.println("Distancia entre nodos: " + distance + " Velocidad entre nodos: " + speed);
            if (distance > 0 && speed > 0) {
                totalTime += (distance*60) / (speed*1000);
                //System.out.println("Tiempo entre nodos: " + totalTime);
            }
        }
        totalTime= Math.round(totalTime * 100.0) / 100.0;
        //System.out.println("Tiempo Dijkstra: " + totalTime);
        return totalTime;
    }
    public double speedBetweenNodes(Long startNodeId, Long endNodeId, GraphController controller) {
        List<Edge> edgeList = controller.getEdges();
        for (Edge edge : edgeList) {
            if (edge.getU() == startNodeId && edge.getV() == endNodeId) {
                return edge.getMaxSpeed();
            }
        }
        return 0;
    }
     public List<GraphPath<Long, DefaultWeightedEdge>> getKShortestPaths(Long startNodeId, Long endNodeId, Graph graph, int k) {
        YenKShortestPath<Long, DefaultWeightedEdge> yenAlg = new YenKShortestPath<>(graph);
         ShortestPathInfo shortestPathInfo = new ShortestPathInfo(longPath(yenAlg.getPaths(startNodeId, endNodeId, k).get(0).getVertexList(), new GraphController()), getDijkstraTime(yenAlg.getPaths(startNodeId, endNodeId, k).get(0).getVertexList(), new GraphController()));
         List<ShortestPathInfo> shortestPathInfos = infoToJson.readInfoFromJson("src/main/resources/static/shortestPathInfo.json");
         shortestPathInfos.add(shortestPathInfo);
         infoToJson.writeInfoToJson(shortestPathInfos, "src/main/resources/static/shortestPathInfo.json");
         return yenAlg.getPaths(startNodeId, endNodeId, k);
    }
}
