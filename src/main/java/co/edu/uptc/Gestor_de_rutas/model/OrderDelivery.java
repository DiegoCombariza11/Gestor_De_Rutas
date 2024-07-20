package co.edu.uptc.Gestor_de_rutas.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Document(value = "OrderDelivery")
@Data
public class OrderDelivery {
    @Setter
    @Getter
    @Id
    private String id;
    @Setter
    @Getter
    private Shopper shopper;
    @Setter
    @Getter
    private LocalDate deadLine;
    @Setter
    @Getter
    private State state;
    @Getter
    @Setter
    private String description;
    @Setter
    @Getter
    private String observation;
    @Setter
    @Getter
    private Package pack;
    private String Destination;

    public OrderDelivery(String id, Shopper shopper, LocalDate deadLine, State state,
                         String description, String observation,Package pack) {
        this.id = id;
        this.shopper = shopper;
        this.deadLine = deadLine;
        this.state = state;
        this.description = description;
        this.observation = observation;
        this.pack = pack;
    }


}
