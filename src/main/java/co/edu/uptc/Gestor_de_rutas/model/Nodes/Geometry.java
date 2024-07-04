package co.edu.uptc.Gestor_de_rutas.model.Nodes;

public class Geometry {
    private String type;
    private double[] coordinates;

    // Constructor
    public Geometry(String type, double[] coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public Geometry() {
    }

    // Getters y setters
    // Agrega aquí los getters y setters para cada campo

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public void printCoordinates() {
        for (int i = 0; i < coordinates.length; i++) {
            System.out.println(coordinates[i]);
        }
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "type='" + type + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}