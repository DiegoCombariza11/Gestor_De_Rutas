package co.edu.uptc.Gestor_de_rutas.repository;


import co.edu.uptc.Gestor_de_rutas.model.OrderDelivery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDeliveryRepository extends MongoRepository<OrderDelivery,String> {


}
