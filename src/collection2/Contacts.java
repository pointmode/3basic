package collection2;

import java.util.*;

public class Contacts {
    private Map<String, String> nameToPhoneMap; // Имя -> Номер телефона
    private Map<String, String> phoneToNameMap; // Номер телефона -> Имя

    public Contacts() {
        nameToPhoneMap = new HashMap<>();
        phoneToNameMap = new HashMap<>();
    }

    public void addContact(String name, String phone) {
        nameToPhoneMap.put(name, phone);
        phoneToNameMap.put(phone, name);
        System.out.println("контакт добавлен: " + name + " - " + phone);
    }

    public void findContactByName(String name) {
        if (nameToPhoneMap.containsKey(name)) {
            System.out.println("контакт: " + name + " - " + nameToPhoneMap.get(name));
        } else {
            System.out.println("контакт не найден: " + name);
        }
    }

    public void findContactByPhone(String phone) {
        if (phoneToNameMap.containsKey(phone)) {
            System.out.println("контакт: " + phoneToNameMap.get(phone) + " - " + phone);
        } else {
            System.out.println("контакт не найден: " + phone);
        }
    }

    public void listContacts() {
        if (nameToPhoneMap.isEmpty()) {
            System.out.println("телефонная книга пуста.");
            return;
        }

        List<String> sortedNames = new ArrayList<>(nameToPhoneMap.keySet());
        Collections.sort(sortedNames);

        System.out.println("список абонентов:");
        for (String name : sortedNames) {
            System.out.println(name + " - " + nameToPhoneMap.get(name));
        }
    }

    public static void main(String[] args) {
        Contacts phoneBook = new Contacts();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("введите команды: 'add <name> <phone>' для добавления контакта, 'find <name>' поиска по имени, 'findPhone <phone>' по номеру, 'list' вывода списка контактов, 'exit' для выхода.");

        while (true) {
            System.out.print("> ");
            command = scanner.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                break;
            } else if (command.startsWith("add ")) {
                String[] parts = command.split(" ");
                if (parts.length == 3) {
                    String name = parts[1];
                    String phone = parts[2];
                    phoneBook.addContact(name, phone);
                } else {
                    System.out.println("неправильная команда. попробуйте 'add <name> <phone>'.");
                }
            } else if (command.startsWith("find ")) {
                String name = command.substring(5);
                phoneBook.findContactByName(name);
            } else if (command.startsWith("findPhone ")) {
                String phone = command.substring(10);
                phoneBook.findContactByPhone(phone);
            } else if (command.equalsIgnoreCase("list")) {
                phoneBook.listContacts();
            } else {
                System.out.println("неправильная команда.");
            }
        }

        scanner.close();
        System.out.println("выход.");
    }
}