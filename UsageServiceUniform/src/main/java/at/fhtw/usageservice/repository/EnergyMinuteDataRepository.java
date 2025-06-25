package at.fhtw.usageservice.repository;

import at.fhtw.usageservice.model.EnergyMinuteData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergyMinuteDataRepository extends JpaRepository<EnergyMinuteData, Long> {
}
