
package co.edu.uptc.Gestor_de_rutas.controller;

import co.edu.uptc.Gestor_de_rutas.logic.OrderDeliveryController;
import co.edu.uptc.Gestor_de_rutas.logic.RouteController;
import co.edu.uptc.Gestor_de_rutas.model.*;
import co.edu.uptc.Gestor_de_rutas.model.Package;
import co.edu.uptc.Gestor_de_rutas.repository.OrderDeliveryRepository;
import co.edu.uptc.Gestor_de_rutas.service.OrderDeliveryService;
import co.edu.uptc.Gestor_de_rutas.service.PackageService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private RouteController routeController;
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
        System.out.println("Entro con id"+id);
        Optional<OrderDelivery> order = orderDeliveryRepository.findById(id);
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/showOrders")
    public ResponseEntity<List<OrderDelivery>> showOrders(@CookieValue(value = "orderIds", defaultValue = "") String orderIds) {
        try {
            List<String> orderIdList = new ObjectMapper().readValue(URLDecoder.decode(orderIds, StandardCharsets.UTF_8), new TypeReference<List<String>>() {});
            List<OrderDelivery> orders = new ArrayList<>();

            for (String orderId : orderIdList) {
                Optional<OrderDelivery> order = orderDeliveryRepository.findById(orderId);
                if (order.isPresent()) {
                    orders.add(order.get());
                }
            }
            if (orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                return ResponseEntity.ok(orders);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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
            String destination = payload.get("destination");
            Long osmId = getOsmId(destination+", Sogamoso, Colombia");
            if (osmId == 0L) {
                return ResponseEntity.badRequest().build();
            }
            System.out.println(osmId);
            String personName = payload.get("personName");
            String personLastName = payload.get("personLastName");
            String personEmail = payload.get("personEmail");
            String personPhone = payload.get("personPhone");
            Buyer buyer = new Buyer(personName, personLastName, personEmail, personPhone);
            LocalDate deadLine = LocalDate.now();
            String nameProduct= payload.get("nameProduct");
            String description = payload.get("description");
            String observation = payload.get("observation");
            String price= payload.get("price");
            String weight = payload.get("weight");
            Package pack = new Package(id, nameProduct, Double.parseDouble(price),weight);
            packageService.savePackage(pack);
            OrderDelivery orderDelivery = new OrderDelivery(id, buyer, deadLine, State.PENDING, description, observation, pack, destination+", Sogamoso, Colombia", osmId);
            orderDeliveryRepository.save(orderDelivery);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    public Long getOsmId(String direction) throws UnsupportedEncodingException {
        routeController=new RouteController();
        return routeController.getOsmId(direction);
    }
    @DeleteMapping("/delete")
    public void deleteOrderDeliveryById(@CookieValue("orderId") String orderId) {
        orderDeliveryRepository.deleteById(orderId);
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateOrderDelivery(@RequestBody Map<String, String> payload) {
        try {
            String id = (payload.get("id"));
            String destination = payload.get("destination");
            Long osmId = getOsmId(destination+", Sogamoso, Colombia");
            if (osmId == 0L) {
                return ResponseEntity.badRequest().build();
            }
            String personName = payload.get("personName");
            String personLastName = payload.get("personLastName");
            String personEmail = payload.get("personEmail");
            String personPhone = payload.get("personPhone");
            Buyer buyer = new Buyer(personName, personLastName, personEmail, personPhone);
            LocalDate deadLine = LocalDate.now();
            String nameProduct= payload.get("nameProduct");
            String description = payload.get("description");
            String observation = payload.get("observation");
            String price= payload.get("price");
            String weight = payload.get("weight");
            Package pack = new Package(id, nameProduct, Double.parseDouble(price),weight);
            packageService.savePackage(pack);
            OrderDelivery orderDelivery = new OrderDelivery(id, buyer, deadLine, State.PENDING, description, observation, pack, destination+", Sogamoso, Colombia", osmId);
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
