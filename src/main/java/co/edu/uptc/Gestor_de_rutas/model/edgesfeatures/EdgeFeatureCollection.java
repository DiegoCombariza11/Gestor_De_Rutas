package co.edu.uptc.Gestor_de_rutas.model.edgesfeatures;

import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class EdgeFeatureCollection {
    private String type;
    private String name;
    private EdgeCrs crs;
    private List<EdgeFeature> features;

    // Constructor
    public EdgeFeatureCollection(String type, String name, EdgeCrs crs, List<EdgeFeature> features) {
        this.type = type;
        this.name = name;
        this.crs = crs;
        this.features = features;
    }
    public EdgeFeatureCollection() {
    }

    // Getters
    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public EdgeCrs getCrs() {
        return crs;
    }
    public List<EdgeFeature> getFeatures() {
        return features;
    }


}


