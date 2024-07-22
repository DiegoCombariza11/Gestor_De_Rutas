package co.edu.uptc.Gestor_de_rutas.controller;

import co.edu.uptc.Gestor_de_rutas.logic.AStarAlgorithm;
import co.edu.uptc.Gestor_de_rutas.logic.GraphController;
import co.edu.uptc.Gestor_de_rutas.model.CustomEdge;
import co.edu.uptc.Gestor_de_rutas.model.ShortestPathInfo;
import org.jgrapht.GraphPath;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PathController {

    private final AStarAlgorithm aStarAlgorithm;

    public PathController(GraphController graphController) {
        this.aStarAlgorithm = AStarAlgorithm.getInstance(graphController);
    }

    @GetMapping("/tricoInfo")
    public ShortestPathInfo getTricoInfo() {
        System.out.println("Getting trico info");
        double distance = aStarAlgorithm.getShortestPathInfo().getDistance();
        double time = aStarAlgorithm.getShortestPathInfo().getTime();
        System.out.println("Distance: " + distance + " Time: " + time);
        return new ShortestPathInfo(distance, time);
    }

}
