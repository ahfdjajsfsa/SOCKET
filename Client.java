import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("127.0.0.1", 8080);
                Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true, StandardCharsets.UTF_8)) {
            System.out.println("Connected to server!");
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            writer.println(username);

            String welcome = reader.readLine();
            if (welcome == null) {
                System.out.println("Server disconnected before login finished.");
                return;
            }

            System.out.println(welcome);
            System.out.println("Enter message, or enter exit to quit.");

            while (true) {
                System.out.print("> ");
                String message = scanner.nextLine();
                writer.println(message);

                String reply = reader.readLine();
                if (reply == null) {
                    System.out.println("Server disconnected.");
                    break;
                }

                System.out.println("Received from server: " + reply);

                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }
            }
        } catch (ConnectException e) {
            System.out.println("Cannot connect to server. Make sure Server.java is running on port 8080.");
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
