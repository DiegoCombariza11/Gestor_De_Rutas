package co.edu.uptc.Gestor_de_rutas.service;

import co.edu.uptc.Gestor_de_rutas.model.OrdenDelivery;
import co.edu.uptc.Gestor_de_rutas.model.Package;
import co.edu.uptc.Gestor_de_rutas.model.State;
import co.edu.uptc.Gestor_de_rutas.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/packages")
public class PackageAPIService {
    private final PackageService packageService;

    @PostMapping("/create")
    public ResponseEntity<String> createPackage(@RequestBody Map<String, String> payload) {
        try {
            String id = (payload.get("id"));
            String description = payload.get("description");
            double price = Double.parseDouble(payload.get("price"));
            String weight = payload.get("weight");
            packageService.savePackage(new Package(id, description, price, weight));
            return ResponseEntity.ok("Package created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating package: " + e.getMessage());
        }
    }

    @GetMapping("/deletePackage")
    public void deletePackage(int id) {
        packageService.deletePackage(id);
    }

    @GetMapping("/findPackage")
    public Package findPackageById(int id) {
        return packageService.getPackageById(id).orElse(null);
    }

    @GetMapping("/showPackages")
    public List<Package> showPackages() {
        return packageService.getAllPackages();
    }

    @PostMapping("/changeState")
    public boolean changeState(String state, OrdenDelivery ordenDelivery) {
        if (state.equals("Entregado")) {
            ordenDelivery.setState(State.DELIVERED);
            return true;
        } else if (state.equals("En camino")) {
            ordenDelivery.setState(State.SHIPPED);
            return true;
        } else if (state.equals("Devuelto")) {
            ordenDelivery.setState(State.CANCELED);
            return true;
        } else if (state.equals("Saliendo de bodega")) {
            ordenDelivery.setState(State.PENDING);
            return true;
        } else {
            return false;
        }
    }

}
