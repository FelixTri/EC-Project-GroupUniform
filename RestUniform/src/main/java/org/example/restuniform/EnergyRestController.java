package org.example.restuniform;

import org.example.restuniform.entity.EnergyHourlyUsage;
import org.example.restuniform.entity.EnergyPercentage;
import org.example.restuniform.repository.EnergyHourlyUsageRepository;
import org.example.restuniform.repository.EnergyPercentageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/postgres")
public class EnergyRestController {

    @Autowired
    private EnergyPercentageRepository percentageRepo;

    @Autowired
    private EnergyHourlyUsageRepository hourlyRepo;

    // GET /postgres/current
    @GetMapping("/current")
    public Map<String, Object> getCurrentData() {
        // Letzter Eintrag nach Zeit
        Optional<EnergyPercentage> latest = percentageRepo.findTopByOrderByHourDesc();

        if (latest.isPresent()) {
            EnergyPercentage data = latest.get();
            return Map.of(
                    "community_depleted", data.getCommunityDepleted(),
                    "grid_portion", data.getGridPortion()
            );
        } else {
            return Map.of(
                    "community_depleted", 0.0,
                    "grid_portion", 0.0
            );
        }
    }

    // GET /postgres/historical?start=...&end=...
    @GetMapping("/historical")
    public Map<String, Object> getHistoricalData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<EnergyHourlyUsage> entries = hourlyRepo.findByHourBetween(start, end);

        double totalProduced = entries.stream().mapToDouble(EnergyHourlyUsage::getCommunityProduced).sum();
        double totalUsed = entries.stream().mapToDouble(EnergyHourlyUsage::getCommunityUsed).sum();
        double totalGrid = entries.stream().mapToDouble(EnergyHourlyUsage::getGridUsed).sum();

        return Map.of(
                "community_produced", totalProduced,
                "community_used", totalUsed,
                "grid_used", totalGrid
        );
    }
}
