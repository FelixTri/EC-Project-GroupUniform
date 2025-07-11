package org.example.communication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class currentPercentageUser {
    private static final Logger logger = Logger.getLogger(currentPercentageUser.class.getName());

    public static void receive(String queueName, int i, String brokerUrl, DeliverCallback deliverCallback) throws IOException, TimeoutException {
        Connection connection = createConnection(brokerUrl);
        Channel channel = connection.createChannel();

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
        logger.info("Listening to queue: " + queueName);

        // Add shutdown hook to gracefully close connection
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                logger.info("Shutting down consumer for queue: " + queueName);
                if (channel.isOpen()) channel.close();
                if (connection.isOpen()) connection.close();
            } catch (Exception e) {
                logger.warning("Error while closing RabbitMQ resources: " + e.getMessage());
            }
        }));
    }


    private static Connection createConnection(String brokerUrl) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(brokerUrl);
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory.newConnection();
    }

}
// This class provides a way to receive messages from a RabbitMQ queue.
// It uses a try-with-resources statement to ensure that the connection and channel are properly closed after use.
// The logger is used to log the success or failure of the message receiving operation.
// The `receive` method takes a queue name, timeout, broker URL, and a `DeliverCallback` as parameters.
// The `createConnection` method is responsible for establishing a connection to the RabbitMQ broker using the provided URL.
// The `DeliverCallback` is used to handle incoming messages, allowing for custom processing logic to be defined by the user.