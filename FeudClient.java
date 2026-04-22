import java.net.*;
import java.io.*;
import java.util.*;

public class FeudClient {

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("localhost", 5000);
                Scanner scanner = new Scanner(System.in)) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // Thread to receive messages
            new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (Exception e) {
                }
            }).start();

            // Send guesses
            while (true) {
                String guess = scanner.nextLine();
                out.println(guess);
            }
        }
    }
}