package co.edu.uptc.Gestor_de_rutas.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(value = "OrderDelivery")
@Data
@Getter
@Setter
public class OrderDelivery {

    @Id
    private String id;
    private Buyer buyer;
    private LocalDate deadLine;
    private State state;
    private String description;
    private String observation;
    private Package pack;
    private String destination;

    public OrderDelivery(String id, Buyer buyer, LocalDate deadLine, State state,
                         String description, String observation, Package pack, String destination) {
        this.id = id;
        this.buyer = buyer;
        this.deadLine = deadLine;
        this.state = state;
        this.description = description;
        this.observation = observation;
        this.pack = pack;
        this.destination = destination;

    }
}
