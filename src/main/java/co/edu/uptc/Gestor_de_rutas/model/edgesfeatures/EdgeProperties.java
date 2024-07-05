package co.edu.uptc.Gestor_de_rutas.model.edgesfeatures;



import java.util.List;

import co.edu.uptc.Gestor_de_rutas.geojsondeserilizer.edges.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;



public class EdgeProperties {
    private long u;
    private long v;
    private int key;
    @JsonDeserialize(using = OsmidDeserializer.class)
    private List<Long> osmid;
    //@JsonDeserialize(using = HighwayDeserializer.class)
    private Object highway;
    private boolean oneway;
    //private boolean reversed;
    //@JsonDeserialize(using = ReversedDeserializer.class)
    private Object reversed;
    private double length;
    private int maxspeed;
    private double weight;
    //@JsonDeserialize(using = LanesDeserializer.class)
    private Object lanes;
    private String ref;
    @JsonDeserialize(using = EdgeNameDeserializer.class)
    private Object name;
    //@JsonDeserialize(using = WidthDeserializer.class)
    private String width;
    private String bridge;
    private String junction;


    // Constructor
    public EdgeProperties(long u, long v, int key, List<Long> osmid, String highway, boolean oneway, Object reversed, double length, int maxspeed, double weight, String lanes, String ref, String name, String width, String bridge, String junction) {
        this.u = u;
        this.v = v;
        this.key = key;
        this.osmid = osmid;
        this.highway = highway;
        this.oneway = oneway;
        this.reversed = reversed;
        this.length = length;
        this.maxspeed = maxspeed;
        this.weight = weight;
        this.lanes = lanes;
        this.ref = ref;
        this.name = name;
        this.width = width;
        this.bridge = bridge;
        this.junction = junction;
    }

    public EdgeProperties() {
    }


    public void setOsmid(List<Long> osmid) {
        this.osmid = osmid;
    }
    
    public List<Long> getOsmid() {
        return osmid;
    }

    public long getU() { return u; }
    public void setU(long u) { this.u = u; }
    public long getV() { return v; }
    public void setV(long v) { this.v = v; }
    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }
    public String getHighway() { return highway.toString(); }
    public void setHighway(Object highway) { this.highway = highway; }
    public boolean isOneway() { return oneway; }
    public void setOneway(boolean oneway) { this.oneway = oneway; }
    public double getLength() { return length; }
    public void setLength(double length) { this.length = length; }
    public int getMaxspeed() { return maxspeed; }
    public void setMaxspeed(int maxspeed) { this.maxspeed = maxspeed; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public String getLanes() { return lanes.toString(); }
    public void setLanes(String lanes) { this.lanes = lanes; }
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }
    public String getName() { return name.toString(); }
    public void setName(String name) { this.name = name; }
    public String getWidth() { return width; }
    public void setWidth(String width) { this.width = width; }
    public String getBridge() { return bridge; }
    public void setBridge(String bridge) { this.bridge = bridge; }
    public String getJunction() { return junction; }
    public void setJunction(String junction) { this.junction = junction; }


     // Getter para reversed
     public Object getReversed() {
        return reversed;
    }

    // Setter para reversed
    public void setReversed(Object reversed) {
        this.reversed = reversed;
    }

    // Métodos de conveniencia para trabajar con reversed
    public Boolean getReversedAsBoolean() {
        if (reversed instanceof Boolean) {
            return (Boolean) reversed;
        }
        // Opcionalmente, manejar el caso de List<Boolean> o devolver null
        return null;
    }

    public List<Boolean> getReversedAsList() {
        if (reversed instanceof List) {
            return (List<Boolean>) reversed;
        }
        // Devolver null o una lista vacía si no es una lista
        return null;
    }
}
