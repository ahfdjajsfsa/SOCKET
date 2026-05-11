import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
            System.out.println("Enter message, or enter exit to quit.");

            while (true) {
                System.out.print("> ");
                String message = scanner.nextLine();

                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }

                writer.println(message);
                String reply = reader.readLine();

                if (reply == null) {
                    System.out.println("Server disconnected.");
                    break;
                }

                System.out.println("Received from server: " + reply);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
