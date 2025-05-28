package at.fhtw.user.controller;

import at.fhtw.user.storage.EnergyStorage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnergyController {

    private final EnergyStorage storage;

    public EnergyController(EnergyStorage storage) {
        this.storage = storage;
    }

    @GetMapping("/energy/total")
    public double getTotalKwh() {
        return storage.getTotalKwh();
    }
}
