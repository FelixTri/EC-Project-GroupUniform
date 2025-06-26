package org.example.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class QueueInitializer {
    public static void main(String[] args) {
        String queueName = "update message";
        String brokerUrl = "localhost";

        try (Connection connection = createConnection(brokerUrl);
             Channel channel = connection.createChannel()) {

            // Declare the queue
            channel.queueDeclare(queueName, true, false, false, null);
            System.out.println("Queue '" + queueName + "' is created or already exists.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Connection createConnection(String brokerUrl) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(brokerUrl);
        return factory.newConnection();
    }
}