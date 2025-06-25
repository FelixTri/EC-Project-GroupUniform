package at.fhtw.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ProducerMessage {

    private String type;
    private String association;
    private double kwh;
    private LocalDateTime datetime;

    public ProducerMessage() {
    }

    @JsonCreator
    public ProducerMessage(
            @JsonProperty("type") String type,
            @JsonProperty("association") String association,
            @JsonProperty("kwh") double kwh,
            @JsonProperty("datetime") LocalDateTime datetime) {
        this.type = type;
        this.association = association;
        this.kwh = kwh;
        this.datetime = datetime;
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

    public LocalDateTime getDatetime() {
        return datetime;
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

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "ProducerMessage{" +
                "type='" + type + '\'' +
                ", association='" + association + '\'' +
                ", kwh=" + kwh +
                ", datetime=" + datetime +
                '}';
    }
}
