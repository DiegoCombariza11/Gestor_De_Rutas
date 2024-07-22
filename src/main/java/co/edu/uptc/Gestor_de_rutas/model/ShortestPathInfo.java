package co.edu.uptc.Gestor_de_rutas.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortestPathInfo {
    private double distance;
    private double time;

    public ShortestPathInfo(double distance, double time) {
        this.distance = distance;
        this.time = time;
    }

    public ShortestPathInfo() {
    }
}
