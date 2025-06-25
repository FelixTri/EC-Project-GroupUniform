package org.example.communication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class currentPercentageProducerUniform {
    private static final Logger logger = Logger.getLogger(currentPercentageProducerUniform.class.getName());

    public static void send(String message, String queueName, String brokerUrl) {
        try (Connection connection = createConnection(brokerUrl);
             Channel channel = connection.createChannel()) {

            channel.basicPublish("", queueName, null, message.getBytes());
            logger.info("Message sent to queue: " + queueName);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to send message to queue: " + queueName, e);
        }
    }

    private static Connection createConnection(String brokerUrl) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(brokerUrl);
        return factory.newConnection();
    }
}
// This class provides a  way to send messages to a RabbitMQ queue.
// It uses an  approach to create a connection and send messages, ensuring that the connection is properly closed after use.
// The logger is used to log the success or failure of the message sending operation.
// The `send` method takes a message, queue name, and broker URL as parameters, and it handles exceptions that may occur during the process.
// The `createConnection` method is responsible for establishing a connection to the RabbitMQ broker using the provided URL.

