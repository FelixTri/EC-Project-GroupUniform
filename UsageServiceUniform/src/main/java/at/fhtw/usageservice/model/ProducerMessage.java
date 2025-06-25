package at.fhtw.usageservice.model;

import java.time.LocalDateTime;

public class ProducerMessage {

    private String type;
    private String association;
    private double kwh;
    private LocalDateTime datetime;

    public ProducerMessage() {}

    public ProducerMessage(String type, String association, double kwh, LocalDateTime datetime) {
        this.type = type;
        this.association = association;
        this.kwh = kwh;
        this.datetime = datetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public double getKwh() {
        return kwh;
    }

    public void setKwh(double kwh) {
        this.kwh = kwh;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}
