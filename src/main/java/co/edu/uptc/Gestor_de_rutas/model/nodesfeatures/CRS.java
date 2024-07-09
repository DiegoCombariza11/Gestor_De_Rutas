package co.edu.uptc.Gestor_de_rutas.model.nodesfeatures;



public class CRS {
    public String type;
    public CRSProperties properties;

    // Constructor
    public CRS(String type, CRSProperties properties) {
        this.type = type;
        this.properties = properties;
    }

    public CRS() {
    }



}