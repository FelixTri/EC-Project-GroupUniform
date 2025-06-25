package at.fhtw.usageservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "energy_minute_data")
public class EnergyMinuteData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String type;
    private String association;
    private double kwh;

    public EnergyMinuteData() {}

    public EnergyMinuteData(LocalDateTime timestamp, String type, String association, double kwh) {
        this.timestamp = timestamp;
        this.type = type;
        this.association = association;
        this.kwh = kwh;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public String getAssociation() {
        return association;
    }

    public double getKwh() {
        return kwh;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public void setKwh(double kwh) {
        this.kwh = kwh;
    }
}
