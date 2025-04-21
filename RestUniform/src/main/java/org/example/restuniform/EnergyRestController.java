package org.example.restuniform;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EnergyRestController {

    @GetMapping("/energy/current")
    public Map<String, Object> getCurrentEnergyStatus() {
        return Map.of(
                "community_depleted", 94.78,
                "grid_portion", 8.94
        );
    }

    @GetMapping("/energy/historical")
    public Map<String, Object> getHistoricalData(
            @RequestParam String start,
            @RequestParam String end
    ) {
        return Map.of(
                "community_produced", 158.321,
                "community_used", 140.874,
                "grid_used", 17.447
        );
    }
}