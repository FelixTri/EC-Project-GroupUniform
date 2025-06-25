package at.fhtw.user.sender;

import at.fhtw.user.model.ProducerMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class RabbitUserSender {

    private final RabbitTemplate rabbitTemplate;
    private final Random random = new Random();

    public RabbitUserSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 5000) // every 5 seconds
    public void send() {
        double kwh = generateKwhBasedOnTime();
        ProducerMessage message = new ProducerMessage(
                "USER",
                "COMMUNITY",
                kwh,
                LocalDateTime.now()
        );

        rabbitTemplate.convertAndSend("energy.queue", message);
        System.out.println("Sent USER message: " + message);
    }

    private double generateKwhBasedOnTime() {
        int hour = LocalDateTime.now().getHour();

        if (hour >= 6 && hour < 9) {
            return 0.005 + random.nextDouble() * 0.01; // Morning peak
        } else if (hour >= 17 && hour < 21) {
            return 0.006 + random.nextDouble() * 0.015; // Evening peak
        } else {
            return 0.001 + random.nextDouble() * 0.004; // Normal
        }
    }
}
