package co.edu.uptc.Gestor_de_rutas.model.Edges;



import java.util.List;

import co.edu.uptc.Gestor_de_rutas.geojsondeserilizer.edges.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;


public class EdgeProperties {
    @Setter
    @Getter
    private long u;
    @Setter
    @Getter
    private long v;
    //private int key;
    @Setter
    @Getter
    @JsonDeserialize(using = OsmidDeserializer.class)
    private List<Long> osmid;
    //public int getKey() { return key; }
    //public void setKey(int key) { this.key = key; }
    //public String getHighway() { return highway.toString(); }
    //public void setHighway(Object highway) { this.highway = highway; }
    //@JsonDeserialize(using = HighwayDeserializer.class)
    //private Object highway;
    @Getter
    private boolean oneway;
    //private boolean reversed;
    //@JsonDeserialize(using = ReversedDeserializer.class)
    //private Object reversed;
    @Getter
    private double length;
    @Getter
    private int maxspeed;
    @Getter
    @Setter
    private double weight;
    //@JsonDeserialize(using = LanesDeserializer.class)
    //private Object lanes;
    //private String ref;
    @JsonDeserialize(using = EdgeNameDeserializer.class)
    private Object name;
    //@JsonDeserialize(using = WidthDeserializer.class)
    //private String width;
    //private String bridge;
    //private String junction;



    public EdgeProperties(long u, long v, List<Long> osmid, boolean oneway, double length, int maxspeed, double weight, String name) {
        this.u = u;
        this.v = v;
        //this.key = key;
        this.osmid = osmid;
        //this.highway = highway;
        this.oneway = oneway;
        //this.reversed = reversed;
        this.length = length;
        this.maxspeed = maxspeed;
        this.weight = weight;
        //this.lanes = lanes;
        //this.ref = ref;
        this.name = name;
        //this.width = width;
        //this.bridge = bridge;
        //this.junction = junction;
    }

    public EdgeProperties() {
    }


   public String getName() { return name.toString(); }
   public void setName(String name) { this.name = name; }


}
