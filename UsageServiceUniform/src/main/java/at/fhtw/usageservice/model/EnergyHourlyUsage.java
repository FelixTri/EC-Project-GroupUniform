package at.fhtw.usageservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "energy_hourly_usage")
public class EnergyHourlyUsage {

    @Id
    private LocalDateTime hour;

    private double communityProduced;
    private double communityUsed;
    private double gridUsed;

    public EnergyHourlyUsage() {}

    public EnergyHourlyUsage(LocalDateTime hour, double communityProduced, double communityUsed, double gridUsed) {
        this.hour = hour;
        this.communityProduced = communityProduced;
        this.communityUsed = communityUsed;
        this.gridUsed = gridUsed;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public double getCommunityProduced() {
        return communityProduced;
    }

    public void setCommunityProduced(double communityProduced) {
        this.communityProduced = communityProduced;
    }

    public double getCommunityUsed() {
        return communityUsed;
    }

    public void setCommunityUsed(double communityUsed) {
        this.communityUsed = communityUsed;
    }

    public double getGridUsed() {
        return gridUsed;
    }

    public void setGridUsed(double gridUsed) {
        this.gridUsed = gridUsed;
    }

    @Override
    public String toString() {
        return "EnergyHourlyUsage{" +
                "hour=" + hour +
                ", communityProduced=" + communityProduced +
                ", communityUsed=" + communityUsed +
                ", gridUsed=" + gridUsed +
                '}';
    }
}
