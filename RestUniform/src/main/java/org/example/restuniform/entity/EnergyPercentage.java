package org.example.restuniform.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "energy_percentage")
public class EnergyPercentage {

    @Id
    private LocalDateTime hour;

    @Column(nullable = false)
    private double communityPool;

    @Column(nullable = false)
    private double gridPortion;

    // Getter und Setter
    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public double getGridPortion() {
        return gridPortion;
    }

    public void setGridPortion(double gridPortion) {
        this.gridPortion = gridPortion;
    }

    public double getCommunityPool() {
        return communityPool;
    }

    public void setCommunityPool(double communityDepleted) {
        this.communityPool = communityDepleted;
    }
}
