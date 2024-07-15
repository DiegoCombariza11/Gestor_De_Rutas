package co.edu.uptc.Gestor_de_rutas.service;

import co.edu.uptc.Gestor_de_rutas.model.*;
import co.edu.uptc.Gestor_de_rutas.model.Package;
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
    @GetMapping("/show")
    public Optional<OrdenDelivery> getOrderDeliveryById(@RequestBody Map<String,String> payload) {
        return orderDeliveryRepository.findById(Integer.parseInt(payload.get("id")));
    }
    @GetMapping("/showAll")
    public List<OrdenDelivery> getAllOrderDeliveries() {
        return orderDeliveryRepository.findAll();
    }
    @PostMapping("/save")
    public ResponseEntity<Void> saveOrderDelivery(@RequestBody Map<String,String> payload) {
        try {
            int id = Integer.parseInt(payload.get("id"));
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
            Package pack = packageService.getPackageById(id).orElse(null);
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
        int id = Integer.parseInt(payload.get("id"));
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
        Package pack = packageService.getPackageById(id).orElse(null);
        OrdenDelivery orderDelivery = new OrdenDelivery(id,shopper,deadLine,state,description,observation,pack);
        orderDeliveryRepository.save(orderDelivery);
    }
    private State parse(String state) {
        if (state.equals("Entregado")) {
            return State.DELIVERED;
        } else if (state.equals("En camino")) {
            return State.ONTHEWAY;
        } else if (state.equals("Devuelto")) {
            return State.RETURNED;
        } else if (state.equals("Saliendo de bodega")) {
            return State.LEAVINGTHEWAREHOUSE;
        } else {
            return null;
        }
    }
}
