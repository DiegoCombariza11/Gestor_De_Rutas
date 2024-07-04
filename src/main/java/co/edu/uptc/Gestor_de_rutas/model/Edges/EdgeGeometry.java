package co.edu.uptc.Gestor_de_rutas.model.Edges;

import java.util.List;

public class EdgeGeometry {
    public String type;
    public List<List<Double>> coordinates;

    // Constructor
    public EdgeGeometry(String type, List<List<Double>> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public EdgeGeometry() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }
    

}
