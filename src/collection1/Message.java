package collection1;

import java.util.*;
import java.util.stream.Collectors;

public class Message {
    private String code;
    private String text;
    private Priority priority;

    public Message(String code, String text, Priority priority) {
        this.code = code;
        this.text = text;
        this.priority = priority;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public Priority getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "сообщение {" +
                "код = '" + code + '\'' +
                ", текст = '" + text + '\'' +
                ", приоритет = " + priority +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(code, message.code) && Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, text);
    }
    enum Priority {
        LOW, MEDIUM, HIGH, URGENT
    }
}