package org.example.restuniform.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "energy_minute_data")
public class EnergyMinuteData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type; // PRODUCER or USER

    @Column(nullable = false)
    private String association;

    @Column(nullable = false)
    private double kwh;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // Getter und Setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getKwh() {
        return kwh;
    }

    public void setKwh(double kwh) {
        this.kwh = kwh;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
