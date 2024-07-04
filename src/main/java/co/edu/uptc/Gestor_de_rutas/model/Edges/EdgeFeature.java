package co.edu.uptc.Gestor_de_rutas.model.Edges;

public class EdgeFeature {
    private String type;
    private int id;
    private EdgeProperties properties;
    private EdgeGeometry geometry;

    // Constructor
    public EdgeFeature(String type, int id, EdgeProperties properties, EdgeGeometry geometry) {
        this.type = type;
        this.id = id;
        this.properties = properties;
        this.geometry = geometry;
    }

    public EdgeFeature() {
    }

    public EdgeProperties getProperties() {
        return properties;
    }

    public void setProperties(EdgeProperties properties) {
        this.properties = properties;
    }

    public EdgeGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(EdgeGeometry geometry) {
        this.geometry = geometry;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    


}
