package co.edu.uptc.Gestor_de_rutas.model;

import co.edu.uptc.Gestor_de_rutas.model.nodesfeatures.Feature;

public class Node {
    private long osmid;
    private int id;
    private double y;
    private double x;

    public Node(Feature feauture) {
        this.osmid = feauture.properties.getOsmid();
        this.id = feauture.id;
        this.y = feauture.properties.getY();
        this.x = feauture.properties.getX();
    }

    public long getOsmid() {
        return osmid;
    }

    public int getId() {
        return id;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
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
