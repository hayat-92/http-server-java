import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
            System.out.println("accepted new connection");
            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
            printWriter.println("HTTP/1.1 200 OK\r\n\r\n");
            InputStream inputStream = clientSocket.getInputStream();
            String requestReader =
                    new BufferedReader(new InputStreamReader(inputStream)).readLine();
            System.out.println("Request content: " + requestReader);
            if (requestReader.contains("GET / HTTP/1.1 200")) {
                printWriter.println("HTTP/1.1 200 OK\r\n\r\n");
            } else {
                printWriter.println("HTTP/1.1 404 Not Found\r\n\r\n");
            }
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}