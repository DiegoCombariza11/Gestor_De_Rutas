package co.edu.uptc.Gestor_de_rutas.model;

import co.edu.uptc.Gestor_de_rutas.model.nodesfeatures.Feature;
import co.edu.uptc.Gestor_de_rutas.model.nodesfeatures.Geometry;
import lombok.Getter;

@Getter
public class Node {
    private final long osmid;
    private final int id;
    private final double y;
    private final double x;
    private Geometry geometry;

    public Node(Feature feauture) {
        this.osmid = feauture.properties.getOsmid();
        this.id = feauture.id;
        this.y = feauture.properties.getY();
        this.x = feauture.properties.getX();
        this.geometry = feauture.geometry;
    }

    @Override
    public String toString() {
        return "Node{" +
                "osmid=" + osmid +
                ", id=" + id +
                ", y=" + y +
                ", x=" + x +
                '}';
    }
}
