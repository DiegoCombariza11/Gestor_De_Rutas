package co.edu.uptc.Gestor_de_rutas.model;

import java.util.ArrayList;

public class Node {

    private int id;
    private Emplecement emplecement;
    private ArrayList<Edge> edges;

    public Node() {
    }

    public Node(int id, Emplecement emplecement, ArrayList<Edge> edges) {
        this.id = id;
        this.emplecement = emplecement;
        this.edges = edges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Emplecement getEmplecement() {
        return emplecement;
    }

    public void setEmplecement(Emplecement emplecement) {
        this.emplecement = emplecement;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

}
