package service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Path;
import java.nio.file.Paths;
public class HttpRequestHandler {
    private String dir;

    public HttpRequestHandler(String dir) {
        this.dir = dir;
    }

    public RawHttpResponse handle(HttpRequest httpRequest) {
        String path = httpRequest.getHttpPath();
        if (path == null || path.strip().isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty");
        }

        if (path.equals("/")) {
            return new RawHttpResponse("HTTP/1.1 200 OK", null, "");
        }

        if (path.startsWith("/user-agent")) {
            String body = httpRequest.getHeaders().get("User-Agent");
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            headers.put("Content-Length", String.valueOf(body.length()));
            return new RawHttpResponse("HTTP/1.1 200 OK", headers, body);
        }

        if (path.startsWith("/echo/")) {
            String body = path.substring(6);
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            headers.put("Content-Length", String.valueOf(body.length()));
            return new RawHttpResponse("HTTP/1.1 200 OK", headers, body);
        }
        dir = dir == null ? "." : dir;

        if(httpRequest.getRequestType().equals(RequestType.POST) && path.startsWith("/files")){
            String fileName = path.substring(7);
            Path filePath = Paths.get(dir + "/" + fileName);
            System.out.println(fileName+" ******************  "+filePath);
            try {
                Files.write(filePath, httpRequest.getBody().getBytes());
                return new RawHttpResponse("HTTP/1.1 201 OK", null, "");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        if (path.startsWith("/files")) {
            String fileName = path.substring(7);
            Path filePath = Paths.get(dir + "/" + fileName);
            if(Files.exists(filePath)){
                try {
                    String body = new String(Files.readAllBytes(filePath));
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/octet-stream");
                    headers.put("Content-Length", String.valueOf(body.length()));
                    return new RawHttpResponse("HTTP/1.1 200 OK", headers, body);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                return new RawHttpResponse("HTTP/1.1 404 Not Found", null, "");
            }

        }
        return new RawHttpResponse("HTTP/1.1 404 Not Found", null, "");
    }
}
