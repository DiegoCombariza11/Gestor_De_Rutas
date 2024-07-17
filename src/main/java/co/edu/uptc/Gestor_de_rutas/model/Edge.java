package co.edu.uptc.Gestor_de_rutas.model;

import co.edu.uptc.Gestor_de_rutas.model.edgesfeatures.EdgeFeature;

public class Edge {
    private String name;
    private long u;
    private long v;
    private double length;
    private double weight;
    private double maxSpeed;

    public Edge(EdgeFeature feature) {
        this.name = feature.getProperties().getName();
        this.u = feature.getProperties().getU();
        this.v = feature.getProperties().getV();
        this.length = feature.getProperties().getLength();
        this.weight = feature.getProperties().getWeight();
        this.maxSpeed = feature.getProperties().getMaxspeed();
    }

    public long getU() {
        return u;
    }

    public long getV() {
        return v;
    }

    public double getLength() {
        return length;
    }

    public double getWeight() {
        return weight;
    }

    public double getMaxSpeed(){return maxSpeed;}

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
