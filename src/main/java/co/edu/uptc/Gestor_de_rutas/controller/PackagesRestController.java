package co.edu.uptc.Gestor_de_rutas.controller;


import co.edu.uptc.Gestor_de_rutas.model.OrderDelivery;
import co.edu.uptc.Gestor_de_rutas.model.Package;
import co.edu.uptc.Gestor_de_rutas.model.State;
import co.edu.uptc.Gestor_de_rutas.service.OrderDeliveryService;
import co.edu.uptc.Gestor_de_rutas.service.PackageService;
import co.edu.uptc.Gestor_de_rutas.service.PathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//acá se manejan las solicitudes http, un tris de lógica pa validaciones nada más y se llaman a los servicios/lógica

@RestController
@RequiredArgsConstructor
@RequestMapping("/packages")
public class PackagesRestController {

    private final PackageService packageService;
    private final PathService pathService;
    private final OrderDeliveryService orderDeliveryService;


    /*
    dont worry, este fue el primer intento para iniciar la ruta
    pero no se usó, se usó el endpoint "/startRoute" o algo así
    pero por si el otro no sirve o algo xd
     */
    @PostMapping("/setEndNodeId")
    public ResponseEntity<String> setEndNodeId(@CookieValue("orderId") String orderId) {
        try {
            Long endNodeId = Long.parseLong(orderId);
            OrderDelivery order = orderDeliveryService.getOrderDeliveryById(orderId).orElse(null);
            assert order != null;
            pathService.setEndNodeID(order.getDestination());
            return ResponseEntity.ok("End node ID set successfully");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid order ID");
        }
    }


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


}
