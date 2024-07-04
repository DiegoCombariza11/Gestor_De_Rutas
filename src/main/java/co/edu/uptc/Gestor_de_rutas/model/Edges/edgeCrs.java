package co.edu.uptc.Gestor_de_rutas.model.Edges;

public class edgeCrs {
    private String type;
    private edgeCrsProperties properties;

    // Constructor
    public edgeCrs(String type, edgeCrsProperties properties) {
        this.type = type;
        this.properties = properties;
    }

    public edgeCrs() {
    }

    // Getters
    public String getType() {
        return type;
    }

    public edgeCrsProperties getProperties() {
        return properties;
    }
}
