package co.edu.uptc.Gestor_de_rutas.model;

import lombok.Getter;


@Getter
public class Buyer {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String contact;

    public Buyer(String firstName, String lastName, String email, String contact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
    }
}
