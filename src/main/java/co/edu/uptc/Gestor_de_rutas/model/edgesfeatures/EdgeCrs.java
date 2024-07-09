package co.edu.uptc.Gestor_de_rutas.model.edgesfeatures;

public class EdgeCrs {
    private String type;
    private EdgeCrsProperties properties;

    // Constructor
    public EdgeCrs(String type, EdgeCrsProperties properties) {
        this.type = type;
        this.properties = properties;
    }

    public EdgeCrs() {
    }

    // Getters
    public String getType() {
        return type;
    }

    public EdgeCrsProperties getProperties() {
        return properties;
    }
}
