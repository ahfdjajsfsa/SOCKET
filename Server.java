import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is listening on port 8080...");

            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Client connected!");

                InputStream inputStream = clientSocket.getInputStream();
                byte[] buffer = new byte[1024];
                int recvLen = inputStream.read(buffer, 0, buffer.length - 1);

                if (recvLen > 0) {
                    String message = new String(buffer, 0, recvLen, StandardCharsets.UTF_8);
                    System.out.println("Received from client: " + message);

                    OutputStream outputStream = clientSocket.getOutputStream();
                    byte[] reply = "Hello, client!".getBytes(StandardCharsets.UTF_8);
                    outputStream.write(reply);
                    outputStream.flush();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
