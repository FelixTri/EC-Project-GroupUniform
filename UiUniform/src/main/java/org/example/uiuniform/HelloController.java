package org.example.uiuniform;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

public class HelloController {

    @FXML
    private Label communityPercentageLabel;

    @FXML
    private Label gridPercentageLabel;

    @FXML
    private DatePicker startPicker;

    @FXML
    private DatePicker endPicker;

    @FXML
    private Label producedLabel;

    @FXML
    private Label usedLabel;

    @FXML
    private Label gridUsedLabel;

    @FXML
    protected void onRefresh() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/energy/current"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());

            double community = json.getDouble("community_depleted");
            double grid = json.getDouble("grid_portion");

            communityPercentageLabel.setText(String.format("%.2f%% used", community));
            gridPercentageLabel.setText(String.format("%.2f%%", grid));
        } catch (Exception e) {
            // Fallback auf Dummy-Daten
            communityPercentageLabel.setText("Simuliert: 82.33% used");
            gridPercentageLabel.setText("Simuliert: 6.71%");
            System.out.println("API nicht erreichbar – Dummy-Daten verwendet (onRefresh)");
        }
    }

    @FXML
    protected void onShowData() {
        LocalDate start = startPicker.getValue();
        LocalDate end = endPicker.getValue();

        if (start == null || end == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select both start and end dates.");
            alert.show();
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String startStr = start.atStartOfDay().format(formatter);
            String endStr = end.atTime(23, 59, 59).format(formatter);

            String url = String.format("http://localhost:8080/energy/historical?start=%s&end=%s", startStr, endStr);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());

            double produced = json.getDouble("community_produced");
            double used = json.getDouble("community_used");
            double gridUsed = json.getDouble("grid_used");

            producedLabel.setText(String.format("Community produced: %.3f kWh", produced));
            usedLabel.setText(String.format("Community used: %.3f kWh", used));
            gridUsedLabel.setText(String.format("Grid used: %.3f kWh", gridUsed));
        } catch (Exception e) {
            // Fallback auf Dummy-Daten
            producedLabel.setText("Simuliert: Community produced: 123.456 kWh");
            usedLabel.setText("Simuliert: Community used: 110.789 kWh");
            gridUsedLabel.setText("Simuliert: Grid used: 12.667 kWh");
            System.out.println("API nicht erreichbar – Dummy-Daten verwendet (onShowData)");
        }
    }
}
