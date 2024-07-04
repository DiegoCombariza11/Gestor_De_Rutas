package co.edu.uptc.Gestor_de_rutas.model;

import java.util.HashMap;
import java.util.Map;

public class Graph {

    private Map<Node, Edge> nodos;
    // contructor

    public Graph(Map<Node, Edge> nodos) {
        this.nodos = new HashMap<>();
        this.nodos = nodos;
    }
    // set y get

    public Map<Node, Edge> getNodos() {
        return nodos;
    }

    public void setNodos(Map<Node, Edge> nodos) {
        this.nodos = nodos;
    }

}
