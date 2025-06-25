package org.example.restuniform.repository;

import org.example.restuniform.entity.EnergyHourlyUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EnergyHourlyUsageRepository extends JpaRepository<EnergyHourlyUsage, LocalDateTime> {
    List<EnergyHourlyUsage> findByHourBetween(LocalDateTime start, LocalDateTime end);
}
