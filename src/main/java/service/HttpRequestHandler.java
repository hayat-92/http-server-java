package service;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHandler {
    public RawHttpResponse handle(HttpRequest httpRequest) {
        String path = httpRequest.getHttpPath();
        if (path == null || path.strip().isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty");
        }

        if (path.equals("/")) {
            return new RawHttpResponse("HTTP/1.1 200 OK", null, "");
        }

        if (path.startsWith("/echo/")) {
            String body = path.substring(6);
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            headers.put("Content-Length", String.valueOf(body.length()));
            return new RawHttpResponse("HTTP/1.1 200 OK", headers, body);
        }
        return new RawHttpResponse("HTTP/1.1 404 Not Found", null, "");
    }
}
