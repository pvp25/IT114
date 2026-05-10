import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {
    private final int PORT = 5000;
    private Map<String, Clienthandler> clients = new ConcurrentHashMap<>();
    private List<Message> history = new ArrayList<>();
    private static final String SAVE_FILE = "messages.dat";

    public ChatServer() {
        history = loadHistory();
    }

    public synchronized boolean registerUser(
            String username,
            Clienthandler handler) {
        if (clients.containsKey(username)) {
            return false;
        }
        clients.put(username, handler);
        broadcast(
                "SERVER",
                username + " joined the chat");
        return true;
    }

    public synchronized void removeUser(
            Clienthandler handler) {
        if (handler.getUsername() != null) {
            clients.remove(handler.getUsername());
            broadcast(
                    "SERVER",
                    handler.getUsername() + " left the chat");
        }
    }

    public void handleMessage(
            Clienthandler sender,
            String raw) {
        if (raw.startsWith("@")) {
            int firstSpace = raw.indexOf(" ");
            if (firstSpace == -1) {
                return;
            }
            String recipient = raw.substring(1, firstSpace);
            String content = raw.substring(firstSpace + 1);
            sendDM(
                    sender.getUsername(),
                    recipient,
                    content);
        } else {
            broadcast(
                    sender.getUsername(),
                    raw);
        }
    }

    public void broadcast(String sender, String content) {

        Message msg = new Message(sender, null, content);

        history.add(msg);

        saveHistory();

        // SEND TO EVERYONE
        for (Clienthandler client : clients.values()) {
            client.sendMessage(msg.toString());
        }
    }

    public void sendChatHistory(Clienthandler client) {

        String username = client.getUsername();

        client.sendMessage("");
        client.sendMessage("===== CHAT HISTORY =====");

        for (Message msg : history) {

            // GLOBAL
            if (msg.isGlobal()) {

                client.sendMessage(
                        msg.toString());
            }

            // PRIVATE DM
            else {

                boolean involved =

                        msg.getSender()
                                .equals(username)

                                ||

                                msg.getRecipient()
                                        .equals(username);

                if (involved) {

                    client.sendMessage(
                            msg.toString());
                }
            }
        }

        client.sendMessage("========================");
        client.sendMessage("");
    }

    public void sendDM(
            String sender,
            String recipient,
            String content) {

        Clienthandler recipientClient = clients.get(recipient);

        Clienthandler senderClient = clients.get(sender);

        // USER DOESN'T EXIST
        if (recipientClient == null) {

            if (senderClient != null) {

                senderClient.sendMessage(
                        "User not found: " + recipient);
            }

            return;
        }

        // CREATE DM
        Message msg = new Message(
                sender,
                recipient,
                content);

        history.add(msg);

        saveHistory();

        System.out.println(msg);

        // SEND ONLY TO SENDER
        senderClient.sendMessage(
                msg.toString());

        // SEND ONLY TO RECIPIENT
        recipientClient.sendMessage(
                msg.toString());
    }

    public void start() {
        try (
                ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println(
                    "Server running on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                Clienthandler handler = new Clienthandler(socket, this);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveHistory() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream(
                                SAVE_FILE))) {
            out.writeObject(history);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Message> loadHistory() {
        try (
                ObjectInputStream in = new ObjectInputStream(
                        new FileInputStream(
                                SAVE_FILE))) {
            return (List<Message>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start();
    }
}