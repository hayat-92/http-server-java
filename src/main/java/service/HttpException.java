package service;

public class HttpException extends RuntimeException {
    private final String code;

    public HttpException(String code, String message) {
        super(message);
        this.code = code;
    }
}
