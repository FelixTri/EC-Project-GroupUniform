package org.example.uiuniform;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HelloController {

    @FXML private Label communityPercentageLabel;
    @FXML private Label gridPercentageLabel;

    @FXML private DatePicker startPicker;
    @FXML private DatePicker endPicker;

    @FXML private ComboBox<Integer> startHourBox;
    @FXML private ComboBox<Integer> endHourBox;

    @FXML private Label producedLabel;
    @FXML private Label usedLabel;
    @FXML private Label gridUsedLabel;

    private final ApiController api = new ApiController();

    @FXML
    public void initialize() {
        for (int h = 0; h < 24; h++) {
            startHourBox.getItems().add(h);
            endHourBox.getItems().add(h);
        }

        startHourBox.setValue(0);
        endHourBox.setValue(23);
    }

    @FXML
    protected void onRefresh() {
        try {
            JSONObject json = api.getCurrentEnergyData();

            double community = json.getDouble("community_depleted");
            double grid = json.getDouble("grid_portion");

            communityPercentageLabel.setText(String.format("Community Pool %.2f%% used", community));
            gridPercentageLabel.setText(String.format("Grid Portion %.2f%%", grid));

        } catch (RuntimeException e) {
            showError("Aktuelle Energiedaten konnten nicht geladen werden:\n\n" + e.getMessage());
            setSimulatedCurrentData();
        }
    }

    @FXML
    protected void onShowData() {
        LocalDate start = startPicker.getValue();
        LocalDate end = endPicker.getValue();
        Integer sh = startHourBox.getValue();
        Integer eh = endHourBox.getValue();

        if (start == null || end == null || sh == null || eh == null) {
            showWarning("Bitte wähle Start- und Enddatum sowie Stunden aus.");
            return;
        }

        try {
            LocalTime startTime = LocalTime.of(sh, 0);
            LocalTime endTime = LocalTime.of(eh, 0);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String startStr = start.atTime(startTime).format(formatter);
            String endStr = end.atTime(endTime).format(formatter);

            JSONObject json = api.getHistoricalEnergyData(startStr, endStr);

            double produced = json.getDouble("community_produced");
            double used = json.getDouble("community_used");
            double gridUsed = json.getDouble("grid_used");

            producedLabel.setText(String.format("Community produced: %.3f kWh", produced));
            usedLabel.setText(String.format("Community used: %.3f kWh", used));
            gridUsedLabel.setText(String.format("Grid used: %.3f kWh", gridUsed));

        } catch (RuntimeException e) {
            showError("Historische Daten konnten nicht geladen werden:\n\n" + e.getMessage());
            setSimulatedHistoricalData();
        }
    }

    private void setSimulatedCurrentData() {
        communityPercentageLabel.setText("Simuliert: 82.33% used");
        gridPercentageLabel.setText("Simuliert: 6.71%");
    }

    private void setSimulatedHistoricalData() {
        producedLabel.setText("Simuliert: Community produced: 123.456 kWh");
        usedLabel.setText("Simuliert: Community used: 110.789 kWh");
        gridUsedLabel.setText("Simuliert: Grid used: 12.667 kWh");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText("Daten konnten nicht geladen werden");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Eingabe fehlt");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
