import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            out = new PrintWriter(
                    socket.getOutputStream(),
                    true // AUTO FLUSH
            );
            // START MESSAGE LISTENER
            Thread readerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String msg;
                        while ((msg = in.readLine()) != null) {
                            System.out.println(msg);
                        }
                    } catch (IOException e) {
                        System.out.println(
                                "Connection closed.");
                    }
                }
            });
            readerThread.start();
            // USER INPUT LOOP
            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    String message = scanner.nextLine();
                    out.println(message);
                }
            }
        } catch (IOException e) {
            System.out.println(
                    "Could not connect to server.");
        }
    }

    public static void main(String[] args) {
        new Client(
                "localhost",
                5000);
    }
}