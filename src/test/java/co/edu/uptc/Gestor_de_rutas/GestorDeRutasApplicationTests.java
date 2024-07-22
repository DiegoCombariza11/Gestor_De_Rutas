package co.edu.uptc.Gestor_de_rutas;

import co.edu.uptc.Gestor_de_rutas.model.OrderDelivery;
import co.edu.uptc.Gestor_de_rutas.service.OrderDeliveryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GestorDeRutasApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private OrderDeliveryService orderDeliveryService;

	@Test
	public void testGetOrderDeliveryById() {
		String id = "1";
		Optional<OrderDelivery> order = orderDeliveryService.getOrderDeliveryById(id);
		System.out.println(order.toString());
		assertTrue(order.isPresent(), "existe");
		assertEquals(id, order.get().getId(), "coincide");
	}

}
