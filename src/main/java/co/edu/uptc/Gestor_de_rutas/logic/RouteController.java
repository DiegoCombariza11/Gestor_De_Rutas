package co.edu.uptc.Gestor_de_rutas.logic;

import co.edu.uptc.Gestor_de_rutas.model.Node;
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
    private GraphController controller;

    public RouteController() {
        this.dijkstraAlgorithm = new DijkstraAlgorithm();
        this.controller = new GraphController();
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

    public List<Long> getPath(List<Long> endPoints, Graph graph, GraphController controller) {
        List<Long> route = setRoute(endPoints, graph, controller);
        List<Long> path = new ArrayList<>();
        for (int i = 0; i < route.size() - 1; i++) {
            List<Long> aux = dijkstraAlgorithm.getShortestPath(route.get(i), route.get(i + 1), graph).getVertexList();
            path.addAll(aux);
        }
        return path;
    }

    public String getOsmId(String direction) throws UnsupportedEncodingException {
        String encodedAddress = URLEncoder.encode(direction, StandardCharsets.UTF_8.toString());
        String nominatimURL = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=geojson&addressdetails=1&limit=1";
        System.out.println(nominatimURL);

        HttpURLConnection conn = null;
        BufferedReader in = null;

        try {
            conn = (HttpURLConnection) new URL(nominatimURL).openConnection();
            conn.setInstanceFollowRedirects(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            System.out.println("Response from Nominatim: " + content.toString());
            String response = content.toString();
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("features")) {
                JSONArray features = jsonObject.getJSONArray("features");
                for (int i = 0; i < features.length(); i++) {
                    JSONObject feature = features.getJSONObject(i);
                    JSONObject geometry = feature.getJSONObject("geometry");
                    JSONArray coordinates = geometry.getJSONArray("coordinates");

                    double lon = coordinates.getDouble(0);
                    double lat = coordinates.getDouble(1);

                    long osmId = findStreet(lat, lon);
                    if (osmId != -1L) {
                        return osmId+""; // Devuelve el ID de OSM si se encuentra una calle
                    }
                }
                System.out.println("No street found for the given address.");
            } else {
                System.out.println("Unexpected response format: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return 0+"";
    }

    public long findStreet(double lat, double lon) {
        long closestVertex = -1L;
        double closestDistance = Double.MAX_VALUE;

        for (Node vertex : this.controller.getNodes()) {
            if (vertex != null) {
                double[] geometry = vertex.getGeometry().getCoordinates();
                double distance = haversine(lat, lon, geometry[1], geometry[0]);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestVertex = vertex.getOsmid();
                }
            }
        }
        return closestVertex;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radio de la Tierra en kilómetros
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
