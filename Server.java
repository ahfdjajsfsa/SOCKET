import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is listening on port 8080...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true,
                                StandardCharsets.UTF_8)) {
                    System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());

                    String username = reader.readLine();
                    if (username == null || username.isBlank()) {
                        username = "Anonymous";
                    }

                    System.out.println(username + " joined.");
                    writer.println("Welcome, " + username + "!");

                    String message;
                    while ((message = reader.readLine()) != null) {
                        if ("exit".equalsIgnoreCase(message)) {
                            System.out.println(username + " left.");
                            writer.println("Goodbye, " + username + "!");
                            break;
                        }

                        System.out.println(username + ": " + message);
                        writer.println("Server received from " + username + ": " + message);
                    }

                    System.out.println("Client disconnected: " + clientSocket.getRemoteSocketAddress());
                } catch (IOException e) {
                    System.out.println("Client connection error: " + e.getMessage());
                }
            }
        } catch (BindException e) {
            System.out.println("Port 8080 is already in use. Stop the other server or use another port.");
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
