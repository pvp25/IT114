import java.net.*;
import java.util.Scanner;
import java.io.*;

class ChatClient {

    public static void main(String[] args) {
        String ipAddress = null; // IP address or domain name of Server
        int port = 1728; // The port on which the server listens.
        Socket connection = null; // For communication with the server.
        BufferedReader incoming; // Stream for receiving data from server.
        PrintWriter outgoing = null; // Stream for sending data to server.
        String messageOut; // A message to be sent to the server.
        String messageIn; // A message received from the server.
        Scanner userInput = null; // Standard input, for reading lines of input from the user.
        userInput = new Scanner(System.in);
        System.out.print("Enter server IP address or domain name: ");
        ipAddress = userInput.nextLine();
        userInput = new Scanner(System.in);
        System.out.print("Enter the IP address or domain name of the server: ");
        ipAddress = userInput.nextLine();
        // 2. Request connection to server on specified host and port
        try {
            System.out.println("Connecting to " + ipAddress + " on port " + port);
            connection = new Socket(ipAddress, port);
            System.out.println("Connected.  Enter your first message.");
        }
        // If connection fails (or invalid ip), print error message, close streams and
        // end the program.
        catch (Exception e) {System.out.println("Failed to connect: " + e.getMessage());
            if (userInput != null) userInput.close();
            return;
        }
        // 3. Exchange messages with the server.
        try {
            System.out.println("NOTE: Enter 'quit' to end the program.\n");
            while (true) {
                System.out.print("SEND:      ");
                incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                outgoing = new PrintWriter(connection.getOutputStream(), true);
                messageOut = userInput.nextLine();
                outgoing.println(messageOut);
                if (messageOut.equals("quit")) {
                    System.out.println("Connection closed.");
                    break;
                }

                // Check for errors while transmitting message.
                if (outgoing.checkError()) {
                    userInput.close();
                    connection.close();
                    throw new IOException("Error occurred while transmitting message.");
                }
                System.out.println("WAITING...");

                messageIn = incoming.readLine();
                if (messageIn == null) {
                    System.out.println("Connection closed by server.");
                    break;
                }
                System.out.println("RECEIVED: " + messageIn);
                if (messageIn.equals("quit")) {
                    System.out.println("Connection closed.");
                    break;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error during communication: " + e.getMessage());
        }

        // 4. Close the connection and end the program, whether error or not.
        finally {
            if (userInput != null) userInput.close();
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {System.out.println("Error closing connection: " + e.getMessage());
            }
        }

    } // end main()
} // end class ChatClient