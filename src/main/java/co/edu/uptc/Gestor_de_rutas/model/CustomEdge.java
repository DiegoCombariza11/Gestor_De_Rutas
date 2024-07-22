package co.edu.uptc.Gestor_de_rutas.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomEdge {
    private Long source;
    private Long target;
    private double distance;
    private double maxSpeed;
    private double time;

    public CustomEdge(Long source, Long target, double distance, double maxSpeed) {
        this.source = source;
        this.target = target;
        this.distance = distance;
        this.maxSpeed = maxSpeed;
        this.time = (distance*60) / (maxSpeed*1000);
    }

    @Override
    public String toString() {
        return "CustomEdge{" +
                "source=" + source +
                ", target=" + target +
                ", distance=" + distance +
                ", maxSpeed=" + maxSpeed +
                ", time=" + time +
                '}';
    }
}
