package co.edu.uptc.Gestor_de_rutas;

import co.edu.uptc.Gestor_de_rutas.controller.DijkstraAlgorithm;
import co.edu.uptc.Gestor_de_rutas.controller.GraphController;

public class Main {
    public static void main(String[] args) {

       GraphController graphController = new GraphController();
       graphController.createGraph();
        GraphController controller = new GraphController();
        controller.createGraph();
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm();
        Long startNodeId = 1016196839L;
        Long endNodeId = 7787924883L;
        double distance = dijkstraAlgorithm.dijkstra(startNodeId, endNodeId, controller.getGraph());
        System.out.println("Camino más corto: " + dijkstraAlgorithm.getShortestPath(startNodeId, endNodeId, controller.getGraph()));
        System.out.println("Distancia total del camino más corto: " + dijkstraAlgorithm.longPath(dijkstraAlgorithm.getShortestPath(startNodeId, endNodeId, controller.getGraph()), controller));
    }

}
