package co.edu.uptc.Gestor_de_rutas.service;

import co.edu.uptc.Gestor_de_rutas.controller.OrderDeliveryController;
import co.edu.uptc.Gestor_de_rutas.model.*;
import co.edu.uptc.Gestor_de_rutas.model.Package;
import co.edu.uptc.Gestor_de_rutas.repository.OrderDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orderDelivery")
public class OrderDeliveryService {
    private final OrderDeliveryRepository orderDeliveryRepository;
    private final PackageService packageService;
    private OrderDeliveryController orderDeliveryController;
    @GetMapping("/allOrders")
public ResponseEntity<List<OrdenDelivery>> getAllOrders() {
    List<OrdenDelivery> orders = orderDeliveryRepository.findAll();
       // System.out.println(orders.get(0).toString());
    if (orders == null || orders.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(orders);
}

    @GetMapping("/show/{id}")
    public Optional<OrdenDelivery> getOrderDeliveryById(@PathVariable("id") int id) {
        return orderDeliveryRepository.findById(id);
    }
    @PostMapping("/save")
    public ResponseEntity<Void> saveOrderDelivery(@RequestBody Map<String,String> payload) {
        try {
            String id = (payload.get("id"));
            String direction = payload.get("direction");
            String personName = payload.get("personName");
            String personLastName = payload.get("personLastName");
            String personEmail = payload.get("personEmail");
            int personId = Integer.parseInt(payload.get("personId"));
            int personPhone = Integer.parseInt(payload.get("personPhone"));
            Person person = new Person(personName, personLastName, personEmail, personId, personPhone);
            Shopper shopper = new Shopper(direction, person);
            LocalDate deadLine = LocalDate.parse(payload.get("deadLine"));
            State state = parse(payload.get("state"));
            String description = payload.get("description");
            String observation = payload.get("observation");
            Package pack = packageService.getPackageById(Integer.parseInt(id)).orElse(null);
            OrdenDelivery orderDelivery = new OrdenDelivery(id, shopper, deadLine, state, description, observation, pack);

            orderDeliveryRepository.save(orderDelivery);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/delete")
    public void deleteOrderDeliveryById(@RequestBody Map<String,String> payload) {
        orderDeliveryRepository.deleteById(Integer.parseInt(payload.get("id")));
    }
    @PostMapping("/update")
    public void updateOrderDelivery(@RequestBody Map<String,String> payload) {
        String id = (payload.get("id"));
        String direction= payload.get("direction");
        String personName = payload.get("personName");
        String personLastName = payload.get("personLastName");
        String personEmail = payload.get("personEmail");
        int personId= Integer.parseInt(payload.get("personId"));
        int personPhone = Integer.parseInt(payload.get("personPhone"));
        Person person= new Person(personName,personLastName,personEmail,personId,personPhone);
        Shopper shopper = new Shopper(direction,person);
        LocalDate deadLine = LocalDate.parse(payload.get("deadLine"));
        State state = State.valueOf(payload.get("state"));
        String description = payload.get("description");
        String observation = payload.get("observation");
        Package pack = packageService.getPackageById(Integer.parseInt(id)).orElse(null);
        OrdenDelivery orderDelivery = new OrdenDelivery(id,shopper,deadLine,state,description,observation,pack);
        orderDeliveryRepository.save(orderDelivery);
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
