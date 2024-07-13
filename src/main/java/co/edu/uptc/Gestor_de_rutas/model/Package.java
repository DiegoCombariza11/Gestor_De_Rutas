package co.edu.uptc.Gestor_de_rutas.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Package")
public class Package {
    @Id
    private int id;
    private String description;
    private double price;
    private String weight;

    public Package(int id, String description, double price, String weight) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", weight='" + weight + '\'' +
                '}';
    }
}
