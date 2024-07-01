package co.edu.uptc.Gestor_de_rutas.model;

public class Emplecement {

    private String name;
    private double length;
    private double latitude;

    public Emplecement(String name, double length, double latitude) {
        this.name = name;
        this.length = length;
        this.latitude = latitude;
    }

    public Emplecement() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

}
