package co.edu.uptc.Gestor_de_rutas.geojsondeserilizer.nodes;

import co.edu.uptc.Gestor_de_rutas.model.nodesfeatures.FeatureCollection;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class GeoJsonReader {
    public static FeatureCollection readGeoJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filePath), FeatureCollection.class);
    }
}