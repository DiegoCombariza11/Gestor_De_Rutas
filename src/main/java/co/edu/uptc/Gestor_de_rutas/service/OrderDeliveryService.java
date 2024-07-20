package co.edu.uptc.Gestor_de_rutas.service;

import co.edu.uptc.Gestor_de_rutas.model.State;
import co.edu.uptc.Gestor_de_rutas.repository.OrderDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import co.edu.uptc.Gestor_de_rutas.model.OrderDelivery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
@Service
public class OrderDeliveryService {

    private final OrderDeliveryRepository orderDeliveryRepository;

    public OrderDeliveryService(OrderDeliveryRepository orderDeliveryRepository) {
        this.orderDeliveryRepository = orderDeliveryRepository;
    }

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
