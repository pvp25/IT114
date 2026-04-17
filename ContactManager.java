import java.util.ArrayList;
import java.util.Scanner;

public class ContactManager {
    public static String formatPhoneNumber(String raw) {
        raw = raw.replaceAll("[^0-9]", "");
        if (raw.length() == 10) {
            return raw.substring(0, 3) + "-" + raw.substring(3, 6) + "-" + raw.substring(6);
        }
        return "Invalid Number";
    }

    public static void main(String[] args) {
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Zack Morris", "zack@bayside.edu", "555.123.4567"));
        contacts.add(new Contact("Alice Smith", "alice@test.com", "(555) 999-8888"));
        contacts.add(new Contact("Bob Jones", "bob@test.com", "5551112222"));
        System.out.println("--- Cleaning Data ---");
        for (Contact c : contacts) {
            c.setPhoneNumber(formatPhoneNumber(c.getPhoneNumber()));
            System.out.println(c);
        }
        System.out.println("--- Sorting Data ---");
        for (int i = 0; i < contacts.size() - 1; i++) {
            for (int j = 0; j < contacts.size() - 1 - i; j++) {
                if (contacts.get(j).getName().compareTo(contacts.get(j + 1).getName()) > 0) {
                    Contact temp = contacts.get(j);
                    contacts.set(j, contacts.get(j + 1));
                    contacts.set(j + 1, temp);
                }
            }
        }
        for (Contact c : contacts) {
            System.out.println(c);
        }
        System.out.println("\n--- Search ---");
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter a name to find: ");
        String searchName = scan.nextLine();
        boolean found = false;
        for (Contact c : contacts) {
            if (c.getName().equalsIgnoreCase(searchName)) {
                System.out.println("Found: " + c);
                found = true;
                System.out.println(found ? "Contact found." : "Contact not found.");
                break; // Exit loop once found
            }
            scan.close();
        }
    }
}