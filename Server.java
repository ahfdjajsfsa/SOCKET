import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println("Received from client: " + message);
                        writer.println("Server received: " + message);
                    }

                    System.out.println("Client disconnected: " + clientSocket.getRemoteSocketAddress());
                } catch (IOException e) {
                    System.out.println("Client error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
