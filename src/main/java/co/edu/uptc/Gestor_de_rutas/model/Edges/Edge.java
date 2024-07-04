package co.edu.uptc.Gestor_de_rutas.model.Edges;

public class Edge {
    private long u;
    private long v;
    private double length;

    public Edge(long u, long v, double length) {
        this.u = u;
        this.v = v;
        this.length = length;
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
}
