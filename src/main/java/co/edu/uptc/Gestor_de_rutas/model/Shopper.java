package co.edu.uptc.Gestor_de_rutas.model;

public class Shopper {
    private String direction;

    // contructores

    public Shopper(String direction) {
        this.direction = direction;
    }

    public Shopper() {
    }

    // set get
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}
