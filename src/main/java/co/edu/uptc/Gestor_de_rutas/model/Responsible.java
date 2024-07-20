package co.edu.uptc.Gestor_de_rutas.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class Responsible {

    private ArrayList<OrderDelivery> orderDeliveries;

    public Responsible(ArrayList<OrderDelivery> ordenDeliveries) {
        this.orderDeliveries = ordenDeliveries;
    }

}
