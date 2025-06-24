package org.example.uiuniform;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HelloController {

    @FXML private Label communityPercentageLabel;
    @FXML private Label gridPercentageLabel;

    @FXML private DatePicker startPicker;
    @FXML private DatePicker endPicker;

    @FXML private ComboBox<Integer> startHourBox;
    @FXML private ComboBox<Integer> startMinuteBox;
    @FXML private ComboBox<Integer> endHourBox;
    @FXML private ComboBox<Integer> endMinuteBox;

    @FXML private Label producedLabel;
    @FXML private Label usedLabel;
    @FXML private Label gridUsedLabel;

    private static final HttpClient client = HttpClient.newHttpClient();

    @FXML
    public void initialize() {
        for (int h = 0; h < 24; h++) {
            startHourBox.getItems().add(h);
            endHourBox.getItems().add(h);
        }
        for (int m = 0; m < 60; m += 5) {
            startMinuteBox.getItems().add(m);
            endMinuteBox.getItems().add(m);
        }

        // Standardwerte
        startHourBox.setValue(0);
        startMinuteBox.setValue(0);
        endHourBox.setValue(23);
        endMinuteBox.setValue(55);
    }

    @FXML
    protected void onRefresh() {
        try {
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
            communityPercentageLabel.setText("Simuliert: 82.33% used");
            gridPercentageLabel.setText("Simuliert: 6.71%");
            System.out.println("API nicht erreichbar – Dummy-Daten verwendet (onRefresh)");
        }
    }

    @FXML
    protected void onShowData() {
        LocalDate start = startPicker.getValue();
        LocalDate end = endPicker.getValue();
        Integer sh = startHourBox.getValue();
        Integer sm = startMinuteBox.getValue();
        Integer eh = endHourBox.getValue();
        Integer em = endMinuteBox.getValue();

        if (start == null || end == null || sh == null || sm == null || eh == null || em == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Bitte wähle Datum und Uhrzeit vollständig aus.");
            alert.show();
            return;
        }

        try {
            LocalTime startTime = LocalTime.of(sh, sm);
            LocalTime endTime = LocalTime.of(eh, em);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String startStr = start.atTime(startTime).format(formatter);
            String endStr = end.atTime(endTime).format(formatter);

            String url = String.format("http://localhost:8080/energy/historical?start=%s&end=%s", startStr, endStr);

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
            producedLabel.setText("Simuliert: Community produced: 123.456 kWh");
            usedLabel.setText("Simuliert: Community used: 110.789 kWh");
            gridUsedLabel.setText("Simuliert: Grid used: 12.667 kWh");
            System.out.println("API nicht erreichbar – Dummy-Daten verwendet (onShowData)");
        }
    }
}
