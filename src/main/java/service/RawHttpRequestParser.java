package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RawHttpRequestParser {

    private RawHttpRequestParser() {
    }


    public static HttpRequest parseHttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> httpRequestLines = new ArrayList<>();
        Map<String, String> headers = new HashMap<>();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            if(!httpRequestLines.isEmpty()){
                String[] header = line.split(":");
                headers.put(header[0].strip(), header[1].strip());
            }
            httpRequestLines.add(line);
        }
        if (httpRequestLines.isEmpty()) {
            throw new HttpException("BAD REQUEST", "Invalid request");
        }

        String startLine = httpRequestLines.get(0);
        String[] startLineParts = startLine.split(" ");
        String rType = startLineParts[0];
        RequestType requestType = null;

        try {
            requestType = RequestType.valueOf(rType);
        } catch (IllegalArgumentException e) {
            throw new HttpException("BAD REQUEST", "Invalid request");
        }

        StringBuilder body = new StringBuilder();
        if(requestType.equals(RequestType.POST) && headers.containsKey("Content-Length") && Integer.parseInt(headers.get("Content-Length")) > 0){
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            char[] buffer = new char[contentLength];
            bufferedReader.read(buffer, 0, contentLength);
            body.append(buffer);
        }

        String httpPath = startLineParts[1];
        return new HttpRequest(requestType, httpPath, headers, body.toString());
    }
}
