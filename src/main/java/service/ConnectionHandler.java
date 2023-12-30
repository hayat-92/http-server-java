package service;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ConnectionHandler {
    private final HttpRequestHandler httpRequestHandler = new HttpRequestHandler();

    public void handleConnection(Socket clientSocket) throws IOException {
        HttpRequest httpRequest = RawHttpRequestParser.parseHttpRequest(clientSocket.getInputStream());
        System.out.printf("Http Request: %s", httpRequest.toString());
        RawHttpResponse rawHttpResponse = httpRequestHandler.handle(httpRequest);
        byte[] responseInBytes = rawHttpResponse.toString().getBytes(StandardCharsets.UTF_8);
        clientSocket.getOutputStream().write(responseInBytes);
    }
}
