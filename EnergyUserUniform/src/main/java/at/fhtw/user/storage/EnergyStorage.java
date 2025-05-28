package at.fhtw.user.storage;

import at.fhtw.user.model.ProducerMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EnergyStorage {
    private final List<ProducerMessage> messages = new ArrayList<>();

    public void addMessage(ProducerMessage message) {
        messages.add(message);
    }

    public List<ProducerMessage> getAllMessages() {
        return messages;
    }

    public double getTotalKwh() {
        return messages.stream().mapToDouble(ProducerMessage::getKwh).sum();
    }
}
