package at.fhtw.usageservice.service;

import at.fhtw.usageservice.model.EnergyHourlyUsage;
import at.fhtw.usageservice.model.ProducerMessage;
import at.fhtw.usageservice.repository.EnergyUsageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UsageService {

    private final EnergyUsageRepository usageRepository;
    private static final Logger logger = LoggerFactory.getLogger(UsageService.class);

    public UsageService(EnergyUsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }

    @Transactional
    public void handleProducerMessage(ProducerMessage message) {
        LocalDateTime hour = message.getDatetime().withMinute(0).withSecond(0).withNano(0);

        EnergyHourlyUsage usage = usageRepository.findByHour(hour)
                .orElseGet(() -> {
                    logger.info("Creating new usage record for hour: {}", hour);
                    return new EnergyHourlyUsage(hour, 0.0, 0.0, 0.0);
                });

        double kwh = message.getKwh();
        String type = message.getType();
        String association = message.getAssociation();

        if ("PRODUCER".equalsIgnoreCase(type) && "COMMUNITY".equalsIgnoreCase(association)) {
            usage.setCommunityProduced(usage.getCommunityProduced() + kwh);
        } else if ("USER".equalsIgnoreCase(type) && "COMMUNITY".equalsIgnoreCase(association)) {
            double available = usage.getCommunityProduced() - usage.getCommunityUsed();
            usage.setCommunityUsed(usage.getCommunityUsed() + kwh);
            if (kwh > available) {
                usage.setGridUsed(usage.getGridUsed() + (kwh - available));
            }
        }

        usageRepository.save(usage);
        logger.info("✔ Updated usage: {}", usage);
    }
}
