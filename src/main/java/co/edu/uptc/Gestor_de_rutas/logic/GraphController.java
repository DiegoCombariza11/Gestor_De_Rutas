package co.edu.uptc.Gestor_de_rutas.logic;

import co.edu.uptc.Gestor_de_rutas.geojsondeserilizer.edges.EdgeGeoJsonReader;
import co.edu.uptc.Gestor_de_rutas.geojsondeserilizer.nodes.GeoJsonReader;
import co.edu.uptc.Gestor_de_rutas.model.CustomEdge;
import co.edu.uptc.Gestor_de_rutas.model.Edge;
import co.edu.uptc.Gestor_de_rutas.model.edgesfeatures.EdgeFeature;
import co.edu.uptc.Gestor_de_rutas.model.edgesfeatures.EdgeFeatureCollection;
import co.edu.uptc.Gestor_de_rutas.model.nodesfeatures.Feature;
import co.edu.uptc.Gestor_de_rutas.model.nodesfeatures.FeatureCollection;
import co.edu.uptc.Gestor_de_rutas.model.Node;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GraphController {

    private GeoJsonReader geoJsonReader;
    private EdgeGeoJsonReader edgeGeoJsonReader;
    private List<Node> nodeList;
    private List<Edge> edgeList;
    private Graph<Long, DefaultEdge> graph;
    private Graph<Long, CustomEdge> graphS;

    public GraphController() {
        this.geoJsonReader = new GeoJsonReader();
        this.edgeGeoJsonReader = new EdgeGeoJsonReader();
        this.nodeList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
        createGraph();
        createShortestPathGraph();
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

    public void createGraph() {

        this.graph = new DefaultDirectedWeightedGraph<>(DefaultEdge.class);

        nodeList = createNodeList("src\\main\\java\\co\\edu\\uptc\\Gestor_de_rutas\\util\\nodes.geojson");
        edgeList = createEdgeList("src\\main\\java\\co\\edu\\uptc\\Gestor_de_rutas\\util\\edges.geojson");
        for (Node node : nodeList) {
            graph.addVertex(node.getOsmid());
        }
        for (Edge edge : edgeList) {
            DefaultEdge graphEdge = graph.addEdge(edge.getU(), edge.getV());
            if (graphEdge != null) {
                graph.setEdgeWeight(graphEdge, edge.getWeight());
            }
        }

    }

    public void createShortestPathGraph() {
        this.graphS = new DefaultDirectedWeightedGraph<>(CustomEdge.class);

        nodeList = createNodeList("src\\main\\java\\co\\edu\\uptc\\Gestor_de_rutas\\util\\nodes.geojson");
        edgeList = createEdgeList("src\\main\\java\\co\\edu\\uptc\\Gestor_de_rutas\\util\\edges.geojson");
        for (Node node : nodeList) {
            graphS.addVertex(node.getOsmid());
        }
        for (Edge edge : edgeList) {
            CustomEdge graphEdge = new CustomEdge(edge.getU(), edge.getV(),  edge.getLength(), edge.getMaxSpeed());
            //System.out.println("Arista: " + graphEdge.toString());
            if (graphEdge != null) {
                boolean isEdgeAdded = graphS.addEdge(edge.getU(), edge.getV(), graphEdge);
                if(isEdgeAdded) {
                    double distance = edge.getLength();
                    graphS.setEdgeWeight(graphEdge, distance);
                }else {
                  //  System.out.println("No se pudo agregar la arista");
                }
            }
        }
    }
    public Graph<Long, CustomEdge> getShortesGraph(){
        return graphS;
    }

    public double getMaxSpeedForEdge(String sourceVertex, String targetVertex) {
        long sourceVertexLong = Long.parseLong(sourceVertex);
        long targetVertexLong = Long.parseLong(targetVertex);
        for (Edge edge : edgeList) {
            if ((edge.getU() == sourceVertexLong && edge.getV() == targetVertexLong)
                    || (edge.getV() == sourceVertexLong && edge.getU() == targetVertexLong)) {
                return edge.getMaxSpeed();
            }
        }
        return 50.0;
    }

    public String getNodeString() {
        String nodes = "";
        for (Node node : nodeList) {
            nodes += node.toString() + "\n";
        }
        return nodes;
    }

    public List<Node> getNodes() {
        return nodeList;
    }

    public List<Edge> getEdges() {
        return edgeList;
    }


    public Graph<Long, DefaultEdge> getGraph() {
        return graph;
    }

    public Node getNodeById(Long id) {
        return nodeList.stream()
                .filter(node -> node.getOsmid() == (id))
                .findFirst()
                .orElse(null);
    }

    public void addNode(Node node) {
        nodeList.add(node);
    }

    public void addEdge(Edge edge) {
        edgeList.add(edge);
    }
}
