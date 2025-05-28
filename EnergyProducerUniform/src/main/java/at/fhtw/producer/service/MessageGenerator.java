package at.fhtw.producer.service;

import at.fhtw.producer.model.ProducerMessage;
import at.fhtw.producer.sender.RabbitProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class MessageGenerator {
    private final RabbitProducer producer;
    private final Random random = new Random();

    public MessageGenerator(RabbitProducer producer) {
        this.producer = producer;
    }

    @Scheduled(fixedDelayString = "#{1000 + T(java.util.concurrent.ThreadLocalRandom).current().nextInt(4000)}")
    public void sendEnergyMessage() {
        double kwh = 0.002 + (0.004 * random.nextDouble());
        LocalDateTime now = LocalDateTime.now();
        ProducerMessage message = new ProducerMessage(kwh, now);
        producer.send(message);
    }
}
