package co.edu.uptc.Gestor_de_rutas.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Document(value = "OrderDelivery")
@Data
public class OrdenDelivery {
    @Id
    private String id;
    private Shopper shopper;
    private LocalDate deadLine;
    private State state;
    private String description;
    private String observation;
    private Package pack;

    public OrdenDelivery(String id, Shopper shopper, LocalDate deadLine, State state,
                         String description, String observation,Package pack) {
        this.id = id;
        this.shopper = shopper;
        this.deadLine = deadLine;
        this.state = state;
        this.description = description;
        this.observation = observation;
        this.pack = pack;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public Responsible getResponsible() {
//        return responsible;
//    }
//
//    public void setResponsible(Responsible responsible) {
//        this.responsible = responsible;
//    }

    public Shopper getShopper() {
        return shopper;
    }

    public void setShopper(Shopper shopper) {
        this.shopper = shopper;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

}
