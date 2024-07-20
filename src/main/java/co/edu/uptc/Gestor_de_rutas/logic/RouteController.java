package co.edu.uptc.Gestor_de_rutas.logic;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RouteController {
    private DijkstraAlgorithm dijkstraAlgorithm;

    public RouteController() {
        this.dijkstraAlgorithm = new DijkstraAlgorithm();
    }

    public List<Long> setRoute(List<Long> endpoints, Graph<Long, DefaultEdge> graph, GraphController controller) {
        List<Long> route = new ArrayList<>();
        List<Long> remainingEndpoints = new ArrayList<>(endpoints);

        route.add(remainingEndpoints.remove(0));

        while (!remainingEndpoints.isEmpty()) {
            Long currentEndpoint = route.get(route.size() - 1);
            double shortestDistance = Double.MAX_VALUE;
            Long nextEndpoint = null;

            for (Long endpoint : remainingEndpoints) {
                double distance = dijkstraAlgorithm.longPath(
                        dijkstraAlgorithm.getShortestPath(currentEndpoint, endpoint, graph).getVertexList(),
                        controller
                );
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nextEndpoint = endpoint;
                }
            }

            if (nextEndpoint != null) {
                route.add(nextEndpoint);
                remainingEndpoints.remove(nextEndpoint);
            }
        }

        return route;
    }
    public List<Long> getPath(List<Long> endPoints, Graph graph,GraphController controller){
        List<Long> route= setRoute(endPoints, graph, controller);
        List<Long> path = new ArrayList<>();
        for (int i = 0; i < route.size() - 1; i++) {
            List<Long> aux = dijkstraAlgorithm.getShortestPath(route.get(i), route.get(i + 1), graph).getVertexList();
            path.addAll(aux);
        }
        return path;
    }
    public Long getOsmId(String direction) throws UnsupportedEncodingException {
        String encodedAddress = URLEncoder.encode(direction, StandardCharsets.UTF_8.toString());
        String nominatimURL = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=geojson&addressdetails=1&limit=10";
        System.out.println(nominatimURL);
        try {
            // Send GET request
            HttpURLConnection conn = (HttpURLConnection) new URL(nominatimURL).openConnection();
            conn.setInstanceFollowRedirects(true); // Handle redirections
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Close connections
            in.close();
            conn.disconnect();

            // Print the response for debugging purposes
            System.out.println("Response from Nominatim: " + content.toString());

            // Parse JSON response
            String response = content.toString();
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("features")) {
                JSONArray features = jsonObject.getJSONArray("features");
                boolean foundStreet = false;
                for (int i = 0; i < features.length(); i++) {
                    JSONObject feature = features.getJSONObject(i);
                    JSONObject properties = feature.getJSONObject("properties");
                    String osmType = properties.getString("osm_type");
                    String featureType = properties.optString("class", "");

                    // Check if the feature is a street (highway)
                    if ("way".equals(osmType) && ("highway".equals(featureType))) {
                        long osmId = properties.getLong("osm_id");
                        System.out.println("Found Street:");
                        System.out.println("OSM ID: " + osmId);
                        System.out.println("OSM Type: " + osmType);
                        System.out.println("Feature Type: " + featureType);
                        return osmId;
                    }
                }
                if (!foundStreet) {
                    System.out.println("No street found for the given address.");
                }
            } else {
                System.out.println("Unexpected response format: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
