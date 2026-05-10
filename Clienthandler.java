import java.io.*;
import java.net.Socket;

public class Clienthandler extends Thread {
    private Socket socket;
    private ChatServer server;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public Clienthandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    public String getUsername() {
        return username;
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("ENTER_USERNAME:");
            String name = in.readLine();
            if (!server.registerUser(name, this)) {
                out.println("USERNAME_TAKEN");
                socket.close();
                return;
            }
            username = name;
            out.println("WELCOME " + username);
            server.sendChatHistory(this);
            String msg;
            while ((msg = in.readLine()) != null) {
                server.handleMessage(this, msg);
            }
        } catch (IOException e) {
            System.out.println(username + " disconnected.");
        } finally {
            server.removeUser(this);
        }
    }

    public static void remove(String username2) {
        System.out.println("Removing user: " + username2);
    }
}