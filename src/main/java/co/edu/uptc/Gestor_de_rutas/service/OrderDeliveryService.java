package co.edu.uptc.Gestor_de_rutas.service;

import co.edu.uptc.Gestor_de_rutas.model.State;
import co.edu.uptc.Gestor_de_rutas.repository.OrderDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import co.edu.uptc.Gestor_de_rutas.model.OrderDelivery;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDeliveryService {


    private final OrderDeliveryRepository orderDeliveryRepository;

    public Optional<OrderDelivery> getOrderDeliveryById(String id) {
        return orderDeliveryRepository.findById(id);
    }


    public void updateOrderState(String id, String state) {
        Optional<OrderDelivery> order = orderDeliveryRepository.findById(id);
        if (order.isPresent()) {
            order.get().setState(State.valueOf(state));
            orderDeliveryRepository.save(order.get());
        }
    }


}
