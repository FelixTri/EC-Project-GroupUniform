package org.example.service;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CurrentPercentageService extends CurrentBaseService {

    private static final String DB_CONNECTION =
            "jdbc:postgresql://localhost:5432/postgres?user=disysuser&password=disyspw";

    private final String id;

    public CurrentPercentageService(String inputQueue, String outputQueue, String messageBrokerUrl) {
        super(inputQueue, outputQueue, messageBrokerUrl);
        this.id = UUID.randomUUID().toString();
        logServiceStart();
    }

    @Override
    protected String processMessage(String input) {
        logMessageArrival(input);

        try (Connection conn = connect()) {
            String[] parts = parseInputMessage(input);
            if (parts == null) {
                return "error";
            }

            Timestamp hour = getTruncatedTimestamp(parts[1]);
            ResultSet rs = fetchEnergyUsage(conn, hour);

            if (!rs.next()) {
                logNoDataFound(hour);
                return "error";
            }

            double[] percentages = calculatePercentages(rs);
            int updated = insertOrUpdatePercentages(conn, hour, percentages);

            logUpdateResult(updated);
            return "ok";

        } catch (Exception e) {
            logProcessingError(input, e);
            return "error";
        }
    }

    private void logServiceStart() {
        System.out.println("CurrentPercentageService Worker (" + id + ") started...");
    }

    private void logMessageArrival(String input) {
        System.out.println(">>> PercentageService: Message received: " + input);
    }

    private void logNoDataFound(Timestamp hour) {
        System.err.println("No energy_usage entry found for hour: " + hour);
    }

    private void logUpdateResult(int updated) {
        System.out.println("Inserted/Updated percentage row: " + updated);
    }

    private void logProcessingError(String input, Exception e) {
        System.err.println("PercentageService failed to process: " + input);
        e.printStackTrace();
    }

    private String[] parseInputMessage(String input) {
        String[] parts = input.split(",");
        if (parts.length != 2) {
            System.err.println("Invalid message format: " + input);
            return null;
        }
        return parts;
    }

    private Timestamp getTruncatedTimestamp(String datetime) {
        LocalDateTime parsedDateTime = LocalDateTime.parse(datetime.trim());
        return Timestamp.valueOf(parsedDateTime.truncatedTo(ChronoUnit.HOURS));
    }

    private ResultSet fetchEnergyUsage(Connection conn, Timestamp hour) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("""
            SELECT community_produced, community_used, grid_used
            FROM energy_usage
            WHERE hour = ?
        """);
        stmt.setTimestamp(1, hour);
        return stmt.executeQuery();
    }

    private double[] calculatePercentages(ResultSet rs) throws SQLException {
        double produced = rs.getDouble("community_produced");
        double used = rs.getDouble("community_used");
        double grid = rs.getDouble("grid_used");

        double communityDepleted = (produced == 0) ? ((used == 0) ? 0.0 : 100.0) : 100.0 * (used / produced);
        double gridPortion = (used + grid == 0) ? 0.0 : 100.0 * (grid / (used + grid));

        return new double[]{communityDepleted, gridPortion};
    }

    private int insertOrUpdatePercentages(Connection conn, Timestamp hour, double[] percentages) throws SQLException {
        PreparedStatement insert = conn.prepareStatement("""
            INSERT INTO current_percentage (hour, community_depleted, grid_portion)
            VALUES (?, ?, ?)
            ON CONFLICT (hour) DO UPDATE SET
                community_depleted = EXCLUDED.community_depleted,
                grid_portion = EXCLUDED.grid_portion
        """);
        insert.setTimestamp(1, hour);
        insert.setDouble(2, percentages[0]);
        insert.setDouble(3, percentages[1]);
        return insert.executeUpdate();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_CONNECTION);
    }
}

// This class extends the abstract base service to handle current percentage calculations.
// It connects to a PostgreSQL database to fetch energy usage data and calculate community depletion and grid portion percentages.
// The `processMessage` method parses the input message, retrieves data from the database, calculates percentages, and inserts or updates the results.
// It includes detailed logging for each step of the process, including error handling.
// The service uses a unique ID for each instance to differentiate logs and operations.
// The database connection string is defined as a constant, and the service logs its start and message processing events.
// The `connect` method establishes a connection to the PostgreSQL database using JDBC.
// The service listens to a specified input queue, processes incoming messages, and logs the results.
// The `processMessage` method is responsible for the core logic of fetching data, calculating percentages, and updating the database.
// The service is designed to be robust, handling various error scenarios gracefully and providing clear logging for debugging purposes.
// It uses JDBC for database interactions and handles SQL exceptions appropriately.
// The service is intended to be run in a RabbitMQ consumer context, where it listens for messages on a specified input queue.
