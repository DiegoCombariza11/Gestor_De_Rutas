package co.edu.uptc.Gestor_de_rutas.model;

import java.time.LocalDate;
//import javafx.scene.chart.PieChart.Data;
public class OrdenDelivery {

    private int id;
    private Responsible responsible;
    private Shopper shopper;
    private LocalDate deadLine;
    private State state;
    private String addressee;
    private String description;
    private String observation;
    private Package pack;

    public OrdenDelivery(int id, Responsible responsible, Shopper shopper, LocalDate deadLine, State state,
                         String addressee, String description, String observation,Package pack) {
        this.id = id;
        this.responsible = responsible;
        this.shopper = shopper;
        this.deadLine = deadLine;
        this.state = state;
        this.addressee = addressee;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Responsible getResponsible() {
        return responsible;
    }

    public void setResponsible(Responsible responsible) {
        this.responsible = responsible;
    }

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

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
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
