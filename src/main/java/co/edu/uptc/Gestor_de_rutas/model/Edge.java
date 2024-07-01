package co.edu.uptc.Gestor_de_rutas.model;

public class Edge {
    private Node destinationNode;
    private Node originNodo;
    private double distance;
    private boolean isBidirectional;

    public Edge() {
    }

    public Edge(Node destinationNode, Node originNodo, double distance, boolean isBidirectional) {
        this.destinationNode = destinationNode;
        this.originNodo = originNodo;
        this.distance = distance;
        this.isBidirectional = isBidirectional;
    }

    public Node getDestinationNode() {
        return destinationNode;
    }

    public void setDestinationNode(Node destinationNode) {
        this.destinationNode = destinationNode;
    }

    public Node getOriginNodo() {
        return originNodo;
    }

    public void setOriginNodo(Node originNodo) {
        this.originNodo = originNodo;
    }

    public boolean isBidirectional() {
        return isBidirectional;
    }

    public void setBidirectional(boolean isBidirectional) {
        this.isBidirectional = isBidirectional;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}
