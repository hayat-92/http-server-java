package service;

import java.util.Map;

public class RawHttpResponse {
    private static final String CRLF = "\r\n";
    private final String startLine;
    private final Map<String, String> headers;
    private final String body;

    public RawHttpResponse(String startLine, Map<String, String> headers, String body) {
        this.startLine = startLine;
        this.headers = headers;
        this.body = body;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(startLine).append(CRLF);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append(CRLF);
        }
        result.append(CRLF);
        result.append(body);
        result.append(CRLF);
        return result.toString();

    }
}
