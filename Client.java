import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("127.0.0.1", 8080)) {
            System.out.println("Connected to server!");

            OutputStream outputStream = clientSocket.getOutputStream();
            byte[] message = "Hello, server!".getBytes(StandardCharsets.UTF_8);
            outputStream.write(message);
            outputStream.flush();

            InputStream inputStream = clientSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int recvLen = inputStream.read(buffer, 0, buffer.length - 1);

            if (recvLen > 0) {
                String reply = new String(buffer, 0, recvLen, StandardCharsets.UTF_8);
                System.out.println("Received from server: " + reply);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
