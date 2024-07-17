package co.edu.uptc.Gestor_de_rutas;

import co.edu.uptc.Gestor_de_rutas.model.OrdenDelivery;
import co.edu.uptc.Gestor_de_rutas.model.Package;
import co.edu.uptc.Gestor_de_rutas.model.State;
import co.edu.uptc.Gestor_de_rutas.repository.OrderDeliveryRepository;
import co.edu.uptc.Gestor_de_rutas.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private OrderDeliveryRepository orderDeliveryRepository;

    @Override
    public void run(String... args) throws Exception {
        Package pack1 = new Package("1", "Package 1", 100.0, "2kg");
        Package pack2 = new Package("2", "Package 2", 200.0, "5kg");

        packageRepository.save(pack1);
        packageRepository.save(pack2);

        OrdenDelivery order1 = new OrdenDelivery("1", null, LocalDate.now(), State.SHIPPED, "Order 1", "None", pack1);
        OrdenDelivery order2 = new OrdenDelivery("2", null, LocalDate.now().plusDays(2), State.SHIPPED, "Order 2", "None", pack2);

        orderDeliveryRepository.save(order1);
        orderDeliveryRepository.save(order2);
    }
}
