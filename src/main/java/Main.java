import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible
        // when running tests.
        System.out.println("Logs from your program will appear here!");
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(4221);
            serverSocket.setReuseAddress(true);
            clientSocket = serverSocket.accept(); // Wait for connection from client.
            InputStream is = clientSocket.getInputStream();
            String startLine = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            startLine = in.lines().findFirst().get();
            String path = startLine.split(" ")[1];
            OutputStream o = clientSocket.getOutputStream();
            String response = "HTTP/1.1 200 OK\r\n\r\n";
//            o.write(response.getBytes(StandardCharsets.UTF_8));
            String okResponse = "HTTP/1.1 200 OK\r\n\r\n";
            String notFoundResponse = "HTTP/1.1 404 Not Found\r\n\r\n";
            if ("/".equals(path)) {
                o.write(okResponse.getBytes(StandardCharsets.UTF_8));
            } else {
                o.write(notFoundResponse.getBytes(StandardCharsets.UTF_8));
            }
            System.out.println("closing socket");
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}