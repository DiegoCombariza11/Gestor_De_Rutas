package co.edu.uptc.Gestor_de_rutas.model.nodesfeatures;

public class Feature {
    public String type;
    public int id;
    public FeatureProperties properties;
    public Geometry geometry;

    // Constructor
    public Feature(String type, int id, FeatureProperties properties, Geometry geometry) {
        this.type = type;
        this.id = id;
        this.properties = properties;
        this.geometry = geometry;
    }

    public Feature() {
    }



}
