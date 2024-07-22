package co.edu.uptc.Gestor_de_rutas.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "Package")
public class Package {

    @Id
    private String id;
    private String description;
    private double price;
    private String weight;


    public Package(String id, String description, double price, String weight) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Package{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", weight='" + weight + '\'' +
                '}';
    }
}
