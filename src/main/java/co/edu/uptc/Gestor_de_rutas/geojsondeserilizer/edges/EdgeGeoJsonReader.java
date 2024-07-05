package co.edu.uptc.Gestor_de_rutas.geojsondeserilizer.edges;

import java.io.IOException;
import java.io.File;

import co.edu.uptc.Gestor_de_rutas.model.edgesfeatures.EdgeFeatureCollection;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EdgeGeoJsonReader {
    public static EdgeFeatureCollection readGeoJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File(filePath);
        EdgeFeatureCollection featureCollection = mapper.readValue(jsonFile, EdgeFeatureCollection.class);
        return featureCollection;
    }
} 

