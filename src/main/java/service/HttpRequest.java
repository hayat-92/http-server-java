package service;

import java.util.Map;

public class HttpRequest {
    private RequestType requestType;
    private String httpPath;
    private Map<String, String> headers;
    private String body;

    public HttpRequest(RequestType requestType, String httpPath, Map<String, String> headers, String body) {
        this.requestType = requestType;
        this.httpPath = httpPath;
        this.headers = headers;
        this.body = body;
    }

    public String getHttpPath() {
        return httpPath;
    }

    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
