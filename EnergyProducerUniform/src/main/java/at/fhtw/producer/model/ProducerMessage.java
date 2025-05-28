package at.fhtw.producer.model;

import java.time.LocalDateTime;

public class ProducerMessage {
    private final String type = "PRODUCER";
    private final String association = "COMMUNITY";
    private double kwh;
    private LocalDateTime datetime;

    public ProducerMessage(double kwh, LocalDateTime datetime) {
        this.kwh = kwh;
        this.datetime = datetime;
    }

    public String getType() { return type; }
    public String getAssociation() { return association; }
    public double getKwh() { return kwh; }
    public LocalDateTime getDatetime() { return datetime; }
}
