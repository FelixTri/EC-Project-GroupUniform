package org.example.restuniform.repository;

import org.example.restuniform.entity.EnergyPercentage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface EnergyPercentageRepository extends JpaRepository<EnergyPercentage, LocalDateTime> {
}
