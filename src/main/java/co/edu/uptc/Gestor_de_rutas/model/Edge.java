package co.edu.uptc.Gestor_de_rutas.model;

import co.edu.uptc.Gestor_de_rutas.model.edgesfeatures.EdgeFeature;
import lombok.Getter;

@Getter
public class Edge {
    private final String name;
    private final long u;
    private final long v;
    private final double length;
    private final double weight;
    private final double maxSpeed;

    public Edge(EdgeFeature feature) {
        this.name = feature.getProperties().getName();
        this.u = feature.getProperties().getU();
        this.v = feature.getProperties().getV();
        this.length = feature.getProperties().getLength();
        this.weight = feature.getProperties().getWeight();
        this.maxSpeed = feature.getProperties().getMaxspeed();
    }


    @Override
    public String toString() {
        return "Edge{" +
                "name='" + name + '\'' +
                ", u=" + u +
                ", v=" + v +
                ", length=" + length +
                ", weight=" + weight +
                '}';
    }
}
