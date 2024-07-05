package co.edu.uptc.Gestor_de_rutas.model.graph;

import co.edu.uptc.Gestor_de_rutas.model.Edge;
import co.edu.uptc.Gestor_de_rutas.model.Node;

import java.util.HashMap;
import java.util.Map;

public class Graph {
    private Map<Long, Node> nodes;
    private Map<Long, Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
    }



    public void addNode(Node node) {
        nodes.put(node.getOsmid(), node);
    }

    public void addEdge(Edge edge) {
        edges.put(edge.getU(), edge);
    }

    public Map<Long, Node> getNodes() {
        return nodes;
    }

    public Map<Long, Edge> getEdges() {
        return edges;
    }


}

