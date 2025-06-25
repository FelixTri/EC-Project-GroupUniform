package org.example.restuniform.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "energy_hourly_usage")
public class EnergyHourlyUsage {

    @Id
    private LocalDateTime hour;

    @Column(nullable = false)
    private double communityProduced;

    @Column(nullable = false)
    private double communityUsed;

    @Column(nullable = false)
    private double gridUsed;

    // Getter und Setter
    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public double getGridUsed() {
        return gridUsed;
    }

    public void setGridUsed(double gridUsed) {
        this.gridUsed = gridUsed;
    }

    public double getCommunityUsed() {
        return communityUsed;
    }

    public void setCommunityUsed(double communityUsed) {
        this.communityUsed = communityUsed;
    }

    public double getCommunityProduced() {
        return communityProduced;
    }

    public void setCommunityProduced(double communityProduced) {
        this.communityProduced = communityProduced;
    }
}
