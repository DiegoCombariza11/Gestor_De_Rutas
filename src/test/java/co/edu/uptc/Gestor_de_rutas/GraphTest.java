package co.edu.uptc.Gestor_de_rutas;

import co.edu.uptc.Gestor_de_rutas.controller.GraphController;
import co.edu.uptc.Gestor_de_rutas.model.Edge;
import co.edu.uptc.Gestor_de_rutas.model.Node;
import co.edu.uptc.Gestor_de_rutas.model.Graph;
import co.edu.uptc.Gestor_de_rutas.model.edgesfeatures.EdgeFeature;
import co.edu.uptc.Gestor_de_rutas.model.edgesfeatures.EdgeGeometry;
import co.edu.uptc.Gestor_de_rutas.model.edgesfeatures.EdgeProperties;
import co.edu.uptc.Gestor_de_rutas.model.nodesfeatures.Feature;
import co.edu.uptc.Gestor_de_rutas.model.nodesfeatures.FeatureProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    GraphController graphController;

    @BeforeEach
    void setUp() {
        graphController = new GraphController();
        graphController.createGraph();
    }

    @Test
    void testGraphIsNotNullAfterCreation() {
        assertNotNull(graphController.getGraph());
    }

    @Test
    void testAddNode() {
        FeatureProperties properties = new FeatureProperties(12345L, 1.0, 2.0, 3, "highway");
        Feature feature = new Feature("FeatureType", 1, properties, null);
        Node newNode = new Node(feature);
        graphController.addNode(newNode);
        assertTrue(graphController.getNodes().contains(newNode));
    }

    @Test
    void testAddEdge() {
        FeatureProperties properties1 = new FeatureProperties(123L, 1.0, 2.0, 3, "highway");
        Feature feature1 = new Feature("FeatureType", 1, properties1, null);
        Node node1 = new Node(feature1);
        FeatureProperties properties2 = new FeatureProperties(456L, 3.0, 4.0, 2, "highway");
        Feature feature2 = new Feature("FeatureType", 2, properties2, null);
        Node node2 = new Node(feature2);
        graphController.addNode(node1);
        graphController.addNode(node2);
        EdgeProperties edgeProperties = new EdgeProperties(node1.getOsmid(), node2.getOsmid(), 0, new ArrayList<>(), "highway", false, false, 10.0, 100, 10.0, "2", "EdgeRef", "EdgeName", "5", "bridge", "junction");
        EdgeGeometry edgeGeometry = new EdgeGeometry("LineString", new ArrayList<>());
        EdgeFeature edgeFeature = new EdgeFeature("EdgeFeatureType", 1, edgeProperties, edgeGeometry);
        Edge newEdge = new Edge(edgeFeature);
        graphController.addEdge(newEdge);
        assertTrue(graphController.getEdges().contains(newEdge));
    }
}
