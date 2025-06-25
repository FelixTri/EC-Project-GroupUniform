package org.example.restuniform.repository;

import org.example.restuniform.entity.EnergyPercentage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EnergyPercentageRepository extends JpaRepository<EnergyPercentage, LocalDateTime> {
    Optional<EnergyPercentage> findTopByOrderByHourDesc();
}

