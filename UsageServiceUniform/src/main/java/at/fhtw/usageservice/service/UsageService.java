package at.fhtw.usageservice.service;

import at.fhtw.usageservice.model.EnergyHourlyUsage;
import at.fhtw.usageservice.model.EnergyMinuteData;
import at.fhtw.usageservice.model.ProducerMessage;
import at.fhtw.usageservice.repository.EnergyMinuteDataRepository;
import at.fhtw.usageservice.repository.EnergyUsageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UsageService {

    private final EnergyUsageRepository usageRepository;
    private final EnergyMinuteDataRepository minuteDataRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.update-notification-queue}")
    private String updateNotificationQueue;

    private static final Logger logger = LoggerFactory.getLogger(UsageService.class);

    public UsageService(EnergyUsageRepository usageRepository,
                        EnergyMinuteDataRepository minuteDataRepository,
                        RabbitTemplate rabbitTemplate) {
        this.usageRepository = usageRepository;
        this.minuteDataRepository = minuteDataRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public void handleProducerMessage(ProducerMessage message) {
        // Store raw minute-level data
        minuteDataRepository.save(new EnergyMinuteData(
                message.getDatetime(),
                message.getType(),
                message.getAssociation(),
                message.getKwh()
        ));

        // Normalize to hourly timestamp
        LocalDateTime hour = message.getDatetime().withMinute(0).withSecond(0).withNano(0);
        EnergyHourlyUsage usage = usageRepository.findByHour(hour)
                .orElseGet(() -> new EnergyHourlyUsage(hour, 0.0, 0.0, 0.0));

        if ("PRODUCER".equals(message.getType()) && "COMMUNITY".equals(message.getAssociation())) {
            usage.setCommunityProduced(usage.getCommunityProduced() + message.getKwh());
        } else if ("USER".equals(message.getType()) && "COMMUNITY".equals(message.getAssociation())) {
            double required = message.getKwh();
            double available = usage.getCommunityProduced() - usage.getCommunityUsed();

            usage.setCommunityUsed(usage.getCommunityUsed() + required);
            if (required > available) {
                usage.setGridUsed(usage.getGridUsed() + (required - available));
            }
        }

        usageRepository.save(usage);
        logger.info("✔ Updated usage: {}", usage);

        // Notify update to next service
        rabbitTemplate.convertAndSend(updateNotificationQueue, message);
    }
}
