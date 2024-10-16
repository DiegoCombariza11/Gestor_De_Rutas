package co.edu.uptc.Gestor_de_rutas;

import co.edu.uptc.Gestor_de_rutas.controller.PackagesRestController;
import co.edu.uptc.Gestor_de_rutas.model.Buyer;
import co.edu.uptc.Gestor_de_rutas.model.Package;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

@RequiredArgsConstructor
public class PackageTest {
    private final PackagesRestController controller;
    private Buyer buyer;
    private Package pack;
 
    @BeforeEach
    void setUp() {
        buyer = new Buyer("Juan", "Perez", "juan.perez@a.com","12345");// Assuming Person class exists and has a default constructor
        pack = new Package("1","nose",21.1,"20"); // Assuming Package class exists and has a default constructor
    }

//    @Test
//    void testCreateOrderDelivery() {
//        controller.createOrderDelivery(1, responsible, shopper, LocalDate.now(), State.DELIVERED, "Description", "Observation", pack);
//        assertNotNull(controller.getOrdenDelivery(), "ordenDelivery should not be null after creation");
//    }
//
//    @Test
//    void testChangeStateDelivered() {
//        controller.createOrderDelivery(1, responsible, shopper, LocalDate.now(), State.ONTHEWAY, "Description", "Observation", pack);
//        assertTrue(controller.changeState("Entregado", controller.getOrdenDelivery()), "State should change to DELIVERED");
//        assertEquals(State.DELIVERED, controller.getOrdenDelivery().getState(), "State should be DELIVERED");
//    }
//
//    @Test
//    void testChangeStateInvalid() {
//        controller.createOrderDelivery(1, responsible, shopper, LocalDate.now(), State.ONTHEWAY, "Description", "Observation", pack);
//        assertFalse(controller.changeState("Invalid", controller.getOrdenDelivery()), "Invalid state should return false");
//    }

    // Additional tests for other states (ONTHEWAY, RETURNED, LEAVINGTHEWAREHOUSE) can be added following the same pattern.
}

