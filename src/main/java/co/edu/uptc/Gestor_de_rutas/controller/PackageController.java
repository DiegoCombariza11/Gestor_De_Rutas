package co.edu.uptc.Gestor_de_rutas.controller;

import co.edu.uptc.Gestor_de_rutas.model.*;
import co.edu.uptc.Gestor_de_rutas.model.Package;
import co.edu.uptc.Gestor_de_rutas.service.PackageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequiredArgsConstructor
public class PackageController {
    private List<Package> packages=new ArrayList<>();

    private Package pack;
    private final PackageService packageService ;

    @Getter
    private OrdenDelivery ordenDelivery;

//    public PackageController() {
//        this.packages = new ArrayList<>();
//        this.pack = pack;
//        this.ordenDelivery = ordenDelivery;
//    }
    @PostMapping("/createOrderDelivery")
    public void createOrderDelivery(int id, Responsible responsible, Shopper shopper, LocalDate deadLine, State state,
                                    String description, String observation, Package pack) {
        this.pack=pack;
        this.ordenDelivery = new OrdenDelivery(id, responsible, shopper, deadLine, state, description, observation, pack);
    }

    public void createPackage(int id, String description, double price, String weight) {
        packages.add(new Package(id, description, price, weight));
        packageService.savePackage(new Package(id, description, price, weight));
    }

    public void deletePackage(int id) {
        Package packToDelete = findPackageById(id);
        Package c=packageService.getPackageById(id).get();
        packageService.deletePackage(id);
        packages.remove(packToDelete);
    }

    public Package findPackageById(int id) {
        for (Package p : packages) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public String showPackages() {
        StringBuilder show = new StringBuilder();
        for (Package p : packages) {
            show.append(p.toString()).append("\n");
        }
        return show.toString();
    }


    public boolean changeState(String state, OrdenDelivery ordenDelivery) {
        if (state.equals("Entregado")) {
            ordenDelivery.setState(State.DELIVERED);
            return true;
        } else if (state.equals("En camino")) {
            ordenDelivery.setState(State.ONTHEWAY);
            return true;
        } else if (state.equals("Devuelto")) {
            ordenDelivery.setState(State.RETURNED);
            return true;
        } else if (state.equals("Saliendo de bodega")) {
            ordenDelivery.setState(State.LEAVINGTHEWAREHOUSE);
            return true;
        } else {
            return false;
        }
    }

}
