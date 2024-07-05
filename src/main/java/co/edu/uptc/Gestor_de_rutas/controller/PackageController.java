package co.edu.uptc.Gestor_de_rutas.controller;

import co.edu.uptc.Gestor_de_rutas.model.*;
import co.edu.uptc.Gestor_de_rutas.model.Package;
import lombok.Getter;

import java.time.LocalDate;

public class PackageController {
    private Package pack;
    @Getter
    private OrdenDelivery ordenDelivery;
    public void createOrderDelivery(int id, Responsible responsible, Shopper shopper, LocalDate deadLine, State state,
                                    String description, String observation, Package pack){
        this.ordenDelivery = new OrdenDelivery(id,responsible,shopper,deadLine,state,description,observation,pack);
    }

    public boolean changeState(String state, OrdenDelivery ordenDelivery){
        if (state.equals("Entregado")){
            ordenDelivery.setState(State.DELIVERED);
            return true;
        }else if (state.equals("En camino")){
            ordenDelivery.setState(State.ONTHEWAY);
            return true;
        }else if(state.equals("Devuelto")){
            ordenDelivery.setState(State.RETURNED);
            return true;
        }else if(state.equals("Saliendo de bodega")){
            ordenDelivery.setState(State.LEAVINGTHEWAREHOUSE);
            return true;
        }else{
            return false;
        }
    }

}
