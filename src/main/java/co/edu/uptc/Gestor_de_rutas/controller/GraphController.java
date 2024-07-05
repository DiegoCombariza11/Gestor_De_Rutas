package co.edu.uptc.Gestor_de_rutas.controller;

import co.edu.uptc.Gestor_de_rutas.geojsondeserilizer.edges.EdgeGeoJsonReader;
import co.edu.uptc.Gestor_de_rutas.geojsondeserilizer.nodes.GeoJsonReader;
import co.edu.uptc.Gestor_de_rutas.model.Edges.Edge;
import co.edu.uptc.Gestor_de_rutas.model.Edges.EdgeFeature;
import co.edu.uptc.Gestor_de_rutas.model.Edges.EdgeFeatureCollection;
import co.edu.uptc.Gestor_de_rutas.model.Nodes.Feature;
import co.edu.uptc.Gestor_de_rutas.model.Nodes.FeatureCollection;
import co.edu.uptc.Gestor_de_rutas.model.Nodes.Node;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class GraphController {

    private GeoJsonReader geoJsonReader;
    private EdgeGeoJsonReader edgeGeoJsonReader;
    private List<Node> nodeList;
    private List<Edge> edgeList;
    private Graph<Long, DefaultEdge> graph;

    public GraphController() {
        this.geoJsonReader = new GeoJsonReader();
        this.edgeGeoJsonReader = new EdgeGeoJsonReader();
        this.nodeList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
    }


    public List<Node> createNodeList(String filePath) {
        try {
            FeatureCollection featureCollection = geoJsonReader.readGeoJson(filePath);
            List<Node> nodeList = new ArrayList<>();
            for (Feature feature : featureCollection.getFeatures()) {
                Node node = new Node(feature);
                nodeList.add(node);
            }
            return nodeList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Edge> createEdgeList(String filePath) {
        try {
            EdgeFeatureCollection edgeFeatureCollection = edgeGeoJsonReader.readGeoJson(filePath);
            List<Edge> edgeList = new ArrayList<>();
            for (EdgeFeature edgeFeature : edgeFeatureCollection.getFeatures()) {
                Edge edge = new Edge(edgeFeature);
                edgeList.add(edge);
            }
            return edgeList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createGraph(){

        this.graph = new DefaultDirectedWeightedGraph<>(DefaultEdge.class);

        nodeList = createNodeList("src\\main\\java\\co\\edu\\uptc\\Gestor_de_rutas\\util\\nodes.geojson");
        edgeList = createEdgeList("src\\main\\java\\co\\edu\\uptc\\Gestor_de_rutas\\util\\edges.geojson");
    for (Node node: nodeList) {
        graph.addVertex(node.getOsmid());
    }

        for (Edge edge: edgeList) {
            DefaultEdge graphEdge = graph.addEdge(edge.getU(), edge.getV());
            if (graphEdge != null) {
                graph.setEdgeWeight(graphEdge, edge.getWeight());
            }
        }

    }

    public String getNodes(){

    }








}
