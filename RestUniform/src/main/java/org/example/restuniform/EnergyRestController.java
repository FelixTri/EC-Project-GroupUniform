package org.example.restuniform;

import org.example.restuniform.model.EnergyPercentage;
import org.example.restuniform.model.EnergyUsage;
import org.example.restuniform.repository.EnergyPercentageRepository;
import org.example.restuniform.repository.EnergyUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/energy")
public class EnergyRestController {

    @Autowired
    private EnergyUsageRepository usageRepo;

    @Autowired
    private EnergyPercentageRepository percentageRepo;

    @GetMapping("/current")
    public EnergyPercentage getCurrentHour() {
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        return percentageRepo.findById(now).orElse(null);
    }

    @GetMapping("/historical")
    public List<EnergyUsage> getHistorical(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return usageRepo.findAllByHourBetween(start, end);
    }
}
