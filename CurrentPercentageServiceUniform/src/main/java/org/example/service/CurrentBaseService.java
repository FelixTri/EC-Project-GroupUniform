package org.example.service;

import com.rabbitmq.client.DeliverCallback;
import org.example.communication.currentPercentageUser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public abstract class CurrentBaseService {
    private final String inputQueue;
    private final String messageBrokerUrl;

    public CurrentBaseService(String inputQueue, String outputQueue, String messageBrokerUrl) {
        this.inputQueue = inputQueue;
        this.messageBrokerUrl = messageBrokerUrl;
    }

    public void run() throws IOException, TimeoutException {
        logQueueListening(inputQueue);

        DeliverCallback callback = createDeliverCallback();
        currentPercentageUser.receive(inputQueue, 10000, messageBrokerUrl, callback);

        logSubscription(inputQueue);
    }


    private DeliverCallback createDeliverCallback() {
        return (consumerTag, delivery) -> {
            String receivedMessage = new String(delivery.getBody(), StandardCharsets.UTF_8);
            logMessageReceived(receivedMessage);

            String processedMessage = processMessage(receivedMessage);
            logProcessingResult(processedMessage);
        };
    }

    private void logQueueListening(String queue) {
        System.out.println("Listening to queue: " + queue);
    }

    private void logSubscription(String queue) {
        System.out.println("Subscribed to queue: " + queue);
    }

    private void logMessageReceived(String message) {
        System.out.println(">>> Message received: " + message);
    }

    private void logProcessingResult(String result) {
        System.out.println(">>> Processing result: " + result);
    }

    protected abstract String processMessage(String input);
}


// This code defines an abstract base service class for handling messages from a RabbitMQ queue.
// It includes methods for running the service, creating a callback for message delivery, and logging various events.
// The `processMessage` method is abstract, allowing subclasses to define their own message processing logic.
// The class uses a custom `currentPercentageUser` to handle RabbitMQ connections and message consumption.
// The service listens to a specified input queue, processes incoming messages, and logs the results.
// The `run` method sets up the message consumption and logging, while the `processMessage` method is meant to be implemented by subclasses to handle specific message processing tasks.
//Help of Co-Pilot