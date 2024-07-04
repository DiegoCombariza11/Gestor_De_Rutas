package co.edu.uptc.Gestor_de_rutas.model.Nodes;

import java.util.List;

public class FeatureCollection {
    private String type;
    private String name;
    private CRS crs;
    private List<Feature> features;

    // Constructor
    public FeatureCollection(String type, String name, CRS crs, List<Feature> features) {
        this.type = type;
        this.name = name;
        this.crs = crs;
        this.features = features;
    }
    public FeatureCollection() {
    }

    // Getters
    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public CRS getCrs() {
        return crs;
    }
    public List<Feature> getFeatures() {
        return features;
    }
    

}


