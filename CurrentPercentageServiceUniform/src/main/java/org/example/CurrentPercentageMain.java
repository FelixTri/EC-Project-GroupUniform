package org.example;

import org.example.service.CurrentPercentageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurrentPercentageMain {
    private static final Logger logger = LoggerFactory.getLogger(CurrentPercentageMain.class);

    public static void main(String[] args) {
        try {
            CurrentPercentageService service = new CurrentPercentageService("update message", "", "localhost");
            logger.info("CurrentPercentageService (update message) started");
            service.run();
        } catch (Exception e) {
            logger.error("Error in CurrentPercentageService", e);
        }
    }
}
// This class serves as the entry point for the CurrentPercentageService application.
// It initializes the service with the specified input queue, output queue, and message broker URL.
// The logger is used to log the start of the service and any errors that may occur during its execution.
// The `main` method creates an instance of `CurrentPercentageService` and calls its `run` method to start processing messages.
// The service is designed to handle messages related to current percentage updates, processing them as defined in the service implementation.
// The `CurrentPercentageService` class is expected to extend the `CurrentBaseService` class, which provides the base functionality for message handling.
