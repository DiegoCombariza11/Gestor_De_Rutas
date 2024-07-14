package co.edu.uptc.Gestor_de_rutas.model;

public class Shopper {
    private String direction;
    private Person person;

    public Shopper(String direction, Person person) {
        this.direction = direction;
        this.person = person;
    }

    public Shopper() {
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}
