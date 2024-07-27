//package co.edu.uptc.Gestor_de_rutas;
//
//import co.edu.uptc.Gestor_de_rutas.model.OrderDelivery;
//import co.edu.uptc.Gestor_de_rutas.model.Package;
//import co.edu.uptc.Gestor_de_rutas.model.State;
//import co.edu.uptc.Gestor_de_rutas.model.Buyer;
//import co.edu.uptc.Gestor_de_rutas.repository.OrderDeliveryRepository;
//import co.edu.uptc.Gestor_de_rutas.repository.PackageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    @Autowired
//    private PackageRepository packageRepository;
//
//    @Autowired
//    private OrderDeliveryRepository orderDeliveryRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//
//        List<Package> packages = List.of(
//                new Package("1", "Portátil", 1200.50, "2kg"),
//                new Package("2", "Impresora", 200.00, "5kg"),
//                new Package("3", "Microondas", 150.00, "7kg"),
//                new Package("4", "Licuadora", 50.00, "1kg"),
//                new Package("5", "Libros", 75.00, "3kg")
//        );
//
//
//        packageRepository.saveAll(packages);
//
//
//        List<OrderDelivery> orders = List.of(
//                new OrderDelivery(
//                        "1",
//                        new Buyer("John", "Doe", "john.doe@example.com", "+57 300-123-4567"),
//                        LocalDate.of(2024, 12, 31),
//                        State.PENDING,
//                        "Pedido de electrónicos",
//                        "Manejar con cuidado",
//                        packages.get(0),
//                        "316951892"
//                ),
//                new OrderDelivery(
//                        "2",
//                        new Buyer("Jane", "Smith", "jane.smith@example.com", "+57 310-987-6543"),
//                        LocalDate.of(2024, 12, 15),
//                        State.PENDING,
//                        "Pedido de electrodomésticos",
//                        "Frágil",
//                        packages.get(1),
//                        "1016182972"
//                ),
//                new OrderDelivery(
//                        "3",
//                        new Buyer("Alice", "Johnson", "alice.johnson@example.com", "+57 320-555-5555"),
//                        LocalDate.of(2024, 11, 30),
//                        State.PENDING,
//                        "Pedido de electrodomésticos de cocina",
//                        "Ninguna",
//                        packages.get(2),
//                        "1016185738"
//                ),
//                new OrderDelivery(
//                        "4",
//                        new Buyer("Bob", "Brown", "bob.brown@example.com", "+57 311-321-9876"),
//                        LocalDate.of(2024, 12, 05),
//                        State.PENDING,
//                        "Pedido de electrodomésticos de cocina",
//                        "Devolver al remitente",
//                        packages.get(3),
//                        "1016187413"
//                ),
//                new OrderDelivery(
//                        "5",
//                        new Buyer("Charlie", "Davis", "charlie.davis@example.com", "+57 312-654-0987"),
//                        LocalDate.of(2024, 12, 20),
//                        State.PENDING,
//                        "Pedido de libros",
//                        "Mantener seco",
//                        packages.get(4),
//                        "1016188813"
//                )
//        );
//
//        orderDeliveryRepository.saveAll(orders);
//    }
//}
