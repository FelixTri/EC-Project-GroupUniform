package at.fhtw.user.model;

import java.time.LocalDateTime;

public class ProducerMessage {
    private String type;
    private String association;
    private double kwh;
    private LocalDateTime datetime;

    public String getType() { return type; }
    public String getAssociation() { return association; }
    public double getKwh() { return kwh; }
    public LocalDateTime getDatetime() { return datetime; }

    public void setType(String type) { this.type = type; }
    public void setAssociation(String association) { this.association = association; }
    public void setKwh(double kwh) { this.kwh = kwh; }
    public void setDatetime(LocalDateTime datetime) { this.datetime = datetime; }
}
