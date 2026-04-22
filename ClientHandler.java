import java.net.*;
import java.io.*;

public class ClientHandler extends Thread {

    Socket socket;
    BufferedReader in;
    PrintWriter out;
    String team;

    public ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Assign team randomly
        team = Math.random() < 0.5 ? "A" : "B";
        sendMessage("TEAM:" + team);
    }

    public void run() {
        try {
            String input;
            while ((input = in.readLine()) != null) {
                handleMessage(input);
            }
        } catch (Exception e) {
            System.out.println("Client disconnected.");
        }
    }

    public void handleMessage(String msg) {
        if (msg.startsWith("GUESS:")) {
            String guess = msg.substring(6);
            String result = FeudServer.game.checkAnswer(team, guess);
            FeudServer.broadcast(result);
        }
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }
}