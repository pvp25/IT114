import java.net.*;
import java.util.*;

public class FeudServer {

    static GameState game = new GameState();

    // Thread-safe list
    static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    static final int MAX_CLIENTS = 10; // easily supports 3+

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started...");

            while (true) {
                Socket socket = serverSocket.accept();
                synchronized (clients) {
                    if (clients.size() >= MAX_CLIENTS) {
                        socket.close(); // reject extra clients
                        continue;
                    }

                    ClientHandler client = new ClientHandler(socket);
                    clients.add(client);
                    client.start();

                    System.out.println("Client connected. Total: " + clients.size());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message) {
        synchronized (clients) {
            Iterator<ClientHandler> it = clients.iterator();

            while (it.hasNext()) {
                ClientHandler c = it.next();
                try {
                    c.sendMessage(message);
                } catch (Exception e) {
                    it.remove(); // remove dead client
                }
            }
        }
    }

    public static void removeClient(ClientHandler client) {
        synchronized (clients) {
            clients.remove(client);
            System.out.println("Client removed. Total: " + clients.size());
        }
    }
}