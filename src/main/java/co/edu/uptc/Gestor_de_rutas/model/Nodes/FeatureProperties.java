package co.edu.uptc.Gestor_de_rutas.model.Nodes;

public class FeatureProperties {
    private long osmid;
    private double y;
    private double x;
    private int street_count;
    private String highway;

    // Constructor
    public FeatureProperties(long osmid, double y, double x, int street_count, String highway) {
        this.osmid = osmid;
        this.y = y;
        this.x = x;
        this.street_count = street_count;
        this.highway = highway;
    }

    public FeatureProperties() {
    }

    // Getters y setters
    // Agrega aquí los getters y setters para cada campo
    public long getOsmid() {
        return osmid;
    }

    public void setOsmid(long osmid) {
        this.osmid = osmid;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getStreet_count() {
        return street_count;
    }

    public void setStreet_count(int street_count) {
        this.street_count = street_count;
    }

    public String getHighway() {
        return highway;
    }

    public void setHighway(String highway) {
        this.highway = highway;
    }

    @Override
    public String toString() {
        return "Properties{" +
                "osmid=" + osmid +
                ", y=" + y +
                ", x=" + x +
                ", street_count=" + street_count +
                ", highway='" + highway + '\'' +
                '}';
    }
}
