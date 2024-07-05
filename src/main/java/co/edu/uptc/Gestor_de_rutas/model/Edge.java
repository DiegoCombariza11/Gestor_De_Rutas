package co.edu.uptc.Gestor_de_rutas.model;

import co.edu.uptc.Gestor_de_rutas.model.edgesfeatures.EdgeFeature;

public class Edge {
    private String name;
    private long u;
    private long v;
    private double length;
    private double weight;

    public Edge(EdgeFeature feature) {
        this.name = feature.getProperties().getName();
        this.u = feature.getProperties().getU();
        this.v = feature.getProperties().getV();
        this.length = feature.getProperties().getLength();
        this.weight = feature.getProperties().getWeight();
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
}
