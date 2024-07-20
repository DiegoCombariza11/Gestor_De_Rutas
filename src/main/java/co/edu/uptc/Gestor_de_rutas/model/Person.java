package co.edu.uptc.Gestor_de_rutas.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Person {
    private String name;
    private String lastName;
    private String email;
    private int id;
    private int contact;

    public Person() {

    }

    public Person(String name, String lastName, String email, int id, int contact) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.contact = contact;
    }

}
