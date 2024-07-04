package co.edu.uptc.Gestor_de_rutas.model.Nodes;

public class Node {
    private long osmid;
    private double y;
    private double x;

    public Node(long osmid, double y, double x) {
        this.osmid = osmid;
        this.y = y;
        this.x = x;
    }

    public long getOsmid() {
        return osmid;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }
}
