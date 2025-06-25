package at.fhtw.usageservice.consumer;

import at.fhtw.usageservice.model.ProducerMessage;
import at.fhtw.usageservice.service.UsageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {

    private final UsageService usageService;

    public RabbitConsumer(UsageService usageService) {
        this.usageService = usageService;
    }

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessage(ProducerMessage message) {
        usageService.handleProducerMessage(message);
    }
}
