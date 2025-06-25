package org.example.uiuniform;

import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiController {

    private final HttpClient client;

    public ApiController() {
        this.client = HttpClient.newHttpClient();
    }

    public JSONObject getCurrentEnergyData() {
        return fetchJson("http://localhost:8083/postgres/current");
    }

    public JSONObject getHistoricalEnergyData(String start, String end) {
        String uri = String.format("http://localhost:8083/postgres/historical?start=%s&end=%s", start, end);
        return fetchJson(uri);
    }

    private JSONObject fetchJson(String uri) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return new JSONObject(response.body());
            } else {
                System.err.println("Fehler bei API-Aufruf: " + response.statusCode());
            }

        } catch (Exception e) {
            System.err.println("Fehler beim Aufruf von URI: " + uri);
            e.printStackTrace();
        }

        return null;
    }
}


