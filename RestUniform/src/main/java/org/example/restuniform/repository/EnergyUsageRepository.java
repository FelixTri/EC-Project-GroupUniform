package org.example.restuniform.repository;

import org.example.restuniform.entity.EnergyUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EnergyUsageRepository extends JpaRepository<EnergyUsage, LocalDateTime> {
    List<EnergyUsage> findAllByHourBetween(LocalDateTime start, LocalDateTime end);
}
