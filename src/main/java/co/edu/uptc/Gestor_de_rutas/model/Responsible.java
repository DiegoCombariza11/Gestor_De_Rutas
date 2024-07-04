package co.edu.uptc.Gestor_de_rutas.model;

import java.util.ArrayList;

public class Responsible {
    private ArrayList<OrdenDelivery> ordenDeliveries;

    public Responsible(ArrayList<OrdenDelivery> ordenDeliveries) {
        this.ordenDeliveries = ordenDeliveries;
    }

    public ArrayList<OrdenDelivery> getOrdenDeliveries() {
        return ordenDeliveries;
    }

    public void setOrdenDeliveries(ArrayList<OrdenDelivery> ordenDeliveries) {
        this.ordenDeliveries = ordenDeliveries;
    }

}
