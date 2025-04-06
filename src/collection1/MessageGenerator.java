package collection1;

import java.util.*;

public class MessageGenerator {
    private static final String[] TEXTS = {
            "кчау", "алё алё", "если ты один, нажми на клубничку", "лол", "привет",
            "гаити", "light imgay"
    };
    private static final Random RANDOM = new Random();

    public static List<Message> generateMessages(int count) {
        Set<Message> messages = new LinkedHashSet<>();
        while (messages.size() < count) {
            String code = "код" + RANDOM.nextInt(100);
            String text = TEXTS[RANDOM.nextInt(TEXTS.length)];
            Message.Priority priority = Message.Priority.values()[RANDOM.nextInt(Message.Priority.values().length)];
            messages.add(new Message(code, text, priority));
        }
        return new ArrayList<>(messages);
    }
}