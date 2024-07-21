package co.edu.uptc.Gestor_de_rutas.controller;

import co.edu.uptc.Gestor_de_rutas.logic.OrderDeliveryController;
import co.edu.uptc.Gestor_de_rutas.model.*;
import co.edu.uptc.Gestor_de_rutas.model.Package;
import co.edu.uptc.Gestor_de_rutas.repository.OrderDeliveryRepository;
import co.edu.uptc.Gestor_de_rutas.service.OrderDeliveryService;
import co.edu.uptc.Gestor_de_rutas.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orderDelivery")
public class OrderDeliveryRestController {
    private final OrderDeliveryRepository orderDeliveryRepository;
    private final PackageService packageService;
    private final OrderDeliveryService orderDeliveryService;

    private OrderDeliveryController orderDeliveryController;

    @GetMapping("/allOrders")
    public ResponseEntity<List<OrderDelivery>> getAllOrders() {
        List<OrderDelivery> orders = orderDeliveryRepository.findAll();
        // System.out.println(orders.get(0).toString());
        if (orders == null || orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/updateState")
    public void updateOrderState(@CookieValue("orderId") String id, @RequestBody Map<String, String> body) {
        String state = body.get("state");
        parse(state);
        orderDeliveryService.updateOrderState(id, String.valueOf(parse(state)));
    }

    @GetMapping("/show/{id}")
    public Optional<OrderDelivery> getOrderDeliveryById(@PathVariable("id") String id) {
        return orderDeliveryRepository.findById(id);
    }


    @GetMapping("/showOrder")
    public ResponseEntity<OrderDelivery> showOrder(@CookieValue("orderId") String id) {
        Optional<OrderDelivery> order = orderDeliveryRepository.findById(id);
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/showAll")
    public List<OrderDelivery> getAllOrderDeliveries() {
        return orderDeliveryRepository.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveOrderDelivery(@RequestBody Map<String, String> payload) {
        try {
            String id = (payload.get("id"));
            String personName = payload.get("personName");
            String personLastName = payload.get("personLastName");
            String personEmail = payload.get("personEmail");
            String personPhone = payload.get("personPhone");
            String destination = payload.get("destination");
            Buyer buyer = new Buyer(personName, personLastName, personEmail, personPhone);
            LocalDate deadLine = LocalDate.now();
            String nameProduct= payload.get("nameProduct");
            String description = payload.get("description");
            String observation = payload.get("observation");
            String price= payload.get("price");
            String weight = payload.get("weight");
            Package pack = new Package(id, nameProduct, Double.parseDouble(price),weight);
            packageService.savePackage(pack);
            OrderDelivery orderDelivery = new OrderDelivery(id, buyer, deadLine, State.PENDING, description, observation, pack, destination);
            orderDeliveryRepository.save(orderDelivery);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete")
    public void deleteOrderDeliveryById(@RequestBody Map<String, String> payload) {
        orderDeliveryRepository.deleteById(payload.get("id"));
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateOrderDelivery(@RequestBody Map<String, String> payload) {
        try {
            String id = (payload.get("id"));
            String personName = payload.get("personName");
            String personLastName = payload.get("personLastName");
            String personEmail = payload.get("personEmail");
            String personPhone = payload.get("personPhone");
            String destination = payload.get("destination");
            Buyer buyer = new Buyer(personName, personLastName, personEmail, personPhone);
            LocalDate deadLine = LocalDate.parse(payload.get("deadLine"));
            String description = payload.get("description");
            String observation = payload.get("observation");
            String price= payload.get("price");
            String weight = payload.get("weight");
            Package pack = new Package(id, description, Double.parseDouble(price),weight);
            packageService.updatePackage(pack);
            OrderDelivery orderDelivery = new OrderDelivery(id, buyer, deadLine, State.PENDING, description, observation, pack, destination);
            orderDeliveryRepository.save(orderDelivery);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    private State parse(String state) {
        if (state.equals("Entregado")) {
            return State.DELIVERED;
        } else if (state.equals("En camino")) {
            return State.SHIPPED;
        } else if (state.equals("Devuelto")) {
            return State.CANCELED;
        } else if (state.equals("Saliendo de bodega")) {
            return State.PENDING;
        } else {
            return null;
        }
    }
}
