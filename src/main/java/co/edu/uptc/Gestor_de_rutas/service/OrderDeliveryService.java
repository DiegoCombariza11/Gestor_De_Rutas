package co.edu.uptc.Gestor_de_rutas.service;

import co.edu.uptc.Gestor_de_rutas.model.OrdenDelivery;
import co.edu.uptc.Gestor_de_rutas.model.OrderDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderDeliveryService {
    private final OrderDeliveryRepository orderDeliveryRepository;
    public Optional<OrdenDelivery> getOrderDeliveryById(int id) {
        return orderDeliveryRepository.findById(id);
    }
    public void saveOrderDelivery(OrdenDelivery orderDelivery) {
        orderDeliveryRepository.save(orderDelivery);
    }
    public void deleteOrderDeliveryById(int id) {
        orderDeliveryRepository.deleteById(id);
    }
    public void updateOrderDelivery(OrdenDelivery orderDelivery) {
        orderDeliveryRepository.save(orderDelivery);
    }
}
