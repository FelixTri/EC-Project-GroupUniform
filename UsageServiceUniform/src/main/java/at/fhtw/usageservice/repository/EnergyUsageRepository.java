package at.fhtw.usageservice.repository;

import at.fhtw.usageservice.model.EnergyHourlyUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EnergyUsageRepository extends JpaRepository<EnergyHourlyUsage, LocalDateTime> {
    Optional<EnergyHourlyUsage> findByHour(LocalDateTime hour);
}
