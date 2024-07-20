package co.edu.uptc.Gestor_de_rutas.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Shopper {

    private String direction;
    private Person person;

    public Shopper(String direction, Person person) {
        this.direction = direction;
        this.person = person;
    }


}
