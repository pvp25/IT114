import java.net.*;
import java.io.*;

public class ChatServer {

    public static void main(String[] args) {

        int port = 1728;

        try (ServerSocket listener = new ServerSocket(port)) {
            System.out.println("Server running on port " + port);

            // Keep accepting clients forever
            while (true) {
                Socket connection = listener.accept();
                System.out.println("New client connected: " + connection.getInetAddress());

                // Create a new thread for each client
                ClientHandler handler = new ClientHandler(connection);
                handler.start();
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}

// Thread class to handle each client independently
class ClientHandler extends Thread {

    private Socket connection;

    public ClientHandler(Socket socket) {
        this.connection = socket;
    }

    public void run() {
        try (
                BufferedReader incoming = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                PrintWriter outgoing = new PrintWriter(
                        connection.getOutputStream(), true);) {

            String message;

            outgoing.println("Connected to server. Type 'quit' to exit.");

            while ((message = incoming.readLine()) != null) {
                System.out.println("Client " + connection.getInetAddress() + ": " + message);

                if (message.equalsIgnoreCase("quit")) {
                    outgoing.println("Goodbye.");
                    break;
                }

                // Echo back message (you can replace this with game logic later)
                outgoing.println("Server: " + message);
            }

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        } finally {
            try {
                connection.close();
                System.out.println("Client disconnected: " + connection.getInetAddress());
            } catch (IOException e) {
                // ignore
            }
        }
    }
}