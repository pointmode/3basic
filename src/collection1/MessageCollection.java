package collection1;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageCollection {
    private List<Message> messages;

    public MessageCollection(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getUniqueMessages() {
        return messages.stream().distinct().collect(Collectors.toList());
    }

    public void removeMessagesByPriority(Message.Priority priority) {
        System.out.println("До удаления:");
        messages.forEach(System.out::println);
        messages.removeIf(message -> message.getPriority() == priority);
        System.out.println("После удаления:");
        messages.forEach(System.out::println);
    }

    public void retainMessagesByPriority(Message.Priority priority) {
        System.out.println("До приоритета:");
        messages.forEach(System.out::println);
        messages.removeIf(message -> message.getPriority() != priority);
        System.out.println("После приоритета:");
        messages.forEach(System.out::println);
    }

    public Map<Message.Priority, Long> countMessagesByPriority() {
        return messages.stream()
                .collect(Collectors.groupingBy(Message::getPriority, Collectors.counting()));
    }

    public Map<String, Long> countMessagesByCode() {
        return messages.stream()
                .collect(Collectors.groupingBy(Message::getCode, Collectors.counting()));
    }

    public long countUniqueMessages() {
        return messages.stream().distinct().count();

    }
}