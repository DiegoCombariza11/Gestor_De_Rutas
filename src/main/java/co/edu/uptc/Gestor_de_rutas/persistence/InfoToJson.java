package co.edu.uptc.Gestor_de_rutas.persistence;

import co.edu.uptc.Gestor_de_rutas.model.ShortestPathInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class InfoToJson {


    public void writeInfoToJson(List<ShortestPathInfo> pathsAndInfos, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filePath), pathsAndInfos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteFile(String filePath){
        File file = new File(filePath);
        file.delete();
    }
    public List<ShortestPathInfo> readInfoFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
