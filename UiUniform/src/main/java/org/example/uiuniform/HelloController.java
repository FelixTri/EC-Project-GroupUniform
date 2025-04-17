package org.example.uiuniform;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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

    // Called when Refresh button is clicked
    @FXML
    protected void onRefresh() {
        // Stub: Replace with real REST call
        communityPercentageLabel.setText("78.54% used");
        gridPercentageLabel.setText("7.23%");
    }

    // Called when Show Data button is clicked
    @FXML
    protected void onShowData() {
        LocalDate start = startPicker.getValue();
        LocalDate end = endPicker.getValue();

        if (start == null || end == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select both start and end dates.");
            alert.show();
            return;
        }

        // Stub: Replace with real API call and data
        producedLabel.setText("Community produced: 143.024 kWh");
        usedLabel.setText("Community used: 130.101 kWh");
        gridUsedLabel.setText("Grid used: 14.75 kWh");
    }
}