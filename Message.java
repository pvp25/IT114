import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String sender;
    private String recipient; // null if global
    private String content;
    private LocalDateTime timestamp;

    public Message(String sender, String recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public boolean isGlobal() {
        return recipient == null;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        if (isGlobal()) {
            return "[" + timestamp + "] GLOBAL | " + sender + ": " + content;
        }

        return "[" + timestamp + "] DM | " + sender +
                " -> " + recipient + ": " + content;
    }
}