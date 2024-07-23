package co.edu.uptc.Gestor_de_rutas.controller;

import co.edu.uptc.Gestor_de_rutas.logic.RouteController;
import co.edu.uptc.Gestor_de_rutas.model.OrderDelivery;
import co.edu.uptc.Gestor_de_rutas.model.State;
import co.edu.uptc.Gestor_de_rutas.service.OrderDeliveryService;
import co.edu.uptc.Gestor_de_rutas.service.PathService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainRestController {


    private final OrderDeliveryService orderDeliveryService;
    private final PathService pathService;
    private RouteController routeController;



    /*
    para startRoute simplemente es manejar el fetch cuando
    se le de click a iniciar ruta, se llama a este endpoint
    que recibe la cookie para buscar el id del destino y poder calcular
    las rutas, faltaría revisar esas rutas, el caso
    luego en ese fetch si la respuesta de este endpoint
    "/startRoute" es exitosa, se redirige a la página de mostrar el mapa
    que por cierto sigue fea, toca ponerle colorsitos xde
    ejemplo del fetch según el copilot xdxd:


    document.getElementById('myButton').addEventListener('click', function() {
    fetch('/api/startRoute', {
        method: 'POST',

        // ...otros parámetros de la solicitud
    })
    .then(response => response.json())
    .then(data => {
        if (data.message === 'Ruta okay') {
            window.location.href = '/pages/map.html';
        } else {
            // manejar el error
        }
    })
    .catch((error) => {
        console.error('Error:', error);
    });
});
     */

    @PostMapping("/startRoute")
    public ResponseEntity<String> startRoute(@CookieValue(value = "orderIds", defaultValue = "") String orderIds) {
        routeController= new RouteController();
        try {
            // Parse the JSON string from the cookie
            List<String> orderIdList = new ObjectMapper().readValue(URLDecoder.decode(orderIds, StandardCharsets.UTF_8), new TypeReference<List<String>>() {});
            System.out.println(orderIdList);
            for (String orderId : orderIdList) {
                // el nodo final es el destino de la orden
                OrderDelivery order = orderDeliveryService.getOrderDeliveryById(orderId).orElse(null);
                if (order == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe la orden con id: " + orderId);
                }
                pathService.setEndNodeID(routeController.getOsmId(order.getDestination()));
                System.out.println(order.getDestination());
                // calcular los recorridos y sha
                pathService.shortestPaths();
                pathService.shortestPathAStar();
                // cambiar el estado de la orden
                orderDeliveryService.updateOrderState(orderId, "SHIPPED");
            }

            return ResponseEntity.ok("Ruta iniciada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al procesar las órdenes: " + e.getMessage());
        }
    }



/*
acá la lógica es similar a la de iniciar ruta, se llama el servicio pa
actualzar el estado y sha
 */

    @PostMapping("/endRoute")
    public ResponseEntity<String> endRoute(@CookieValue("orderId") String orderId) {
        try {
            OrderDelivery order = orderDeliveryService.getOrderDeliveryById(orderId).orElse(null);
            if (order == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe la orden con ese id");
            }
            orderDeliveryService.updateOrderState(orderId, "DELIVERED");
            return ResponseEntity.ok("Ruta finalizada");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe la orden con ese id");
        }
    }


}
