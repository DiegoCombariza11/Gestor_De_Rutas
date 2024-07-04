package co.edu.uptc.Gestor_de_rutas;

import co.edu.uptc.Gestor_de_rutas.model.Edges.Edge;
import co.edu.uptc.Gestor_de_rutas.model.Nodes.Node;
import co.edu.uptc.Gestor_de_rutas.model.graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphTest {
    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph();
    }

    @Test
    void testAddNode() {
        Node node = new Node(1L, 10.0, 20.0);
        graph.addNode(node);
        assertEquals(1, graph.getNodes().size());
        assertTrue(graph.getNodes().containsKey(1L));
    }

    @Test
    void testAddEdge() {
        Node node1 = new Node(1L, 10.0, 20.0);
        Node node2 = new Node(2L, 30.0, 40.0);
        graph.addNode(node1);
        graph.addNode(node2);
        co.edu.uptc.Gestor_de_rutas.model.Edges.Edge edge = new Edge(node1.getOsmid(), node2.getOsmid(), 5.0);
        graph.addEdge(edge);
        assertEquals(1, graph.getEdges().size());
        assertTrue(graph.getEdges().containsKey(edge.getU()));
    }
}
