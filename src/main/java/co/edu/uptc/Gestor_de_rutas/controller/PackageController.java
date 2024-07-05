package co.edu.uptc.Gestor_de_rutas.controller;

import co.edu.uptc.Gestor_de_rutas.model.*;
import co.edu.uptc.Gestor_de_rutas.model.Package;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PackageController {
    private List<Package> packages;

    private Package pack;

    @Getter
    private OrdenDelivery ordenDelivery;

    public PackageController() {
        this.packages = new ArrayList<>();
        this.pack = pack;
        this.ordenDelivery = ordenDelivery;
    }


    public void createOrderDelivery(int id, Responsible responsible, Shopper shopper, LocalDate deadLine, State state,
                                    String description, String observation, Package pack){
        this.ordenDelivery = new OrdenDelivery(id,responsible,shopper,deadLine,state,description,observation,pack);
    }

    public void createPackage(int id, String description, double price, String weight ){
        packages.add(new Package(id,description, price, weight));
    }

    public void deletePackage(int id){
        Package packToDelete = findPackageById(id);
            packages.remove(packToDelete);
        }

    public Package findPackageById(int id){
        for (Package p: packages){
            if (p.getId() == id){
                return p;
            }
        }
        return null;
    }
    public String showPackages(){
        StringBuilder show = new StringBuilder();
        for (Package p: packages){
            show.append(p.toString()).append("\n");
        }
        return show.toString();
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
