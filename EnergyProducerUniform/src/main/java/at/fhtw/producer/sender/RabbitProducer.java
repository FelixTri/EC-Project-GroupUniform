package at.fhtw.producer.sender;

import at.fhtw.producer.config.RabbitMQConfig;
import at.fhtw.producer.model.ProducerMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitProducer {
    private final RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(ProducerMessage message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, message);
        System.out.println("Sent: " + message.getKwh() + " kWh @ " + message.getDatetime());
    }
}
