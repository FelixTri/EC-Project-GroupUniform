package at.fhtw.user.consumer;

import at.fhtw.user.model.ProducerMessage;
import at.fhtw.user.storage.EnergyStorage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {

    private final EnergyStorage storage;

    public RabbitConsumer(EnergyStorage storage) {
        this.storage = storage;
    }

    @RabbitListener(queues = "energy.queue")
    public void receive(ProducerMessage message) {
        System.out.println("Received: " + message.getKwh() + " kWh @ " + message.getDatetime());
        storage.addMessage(message);
    }
}
