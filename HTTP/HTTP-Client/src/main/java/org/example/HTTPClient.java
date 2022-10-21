package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class HTTPClient {

    public static void main(String[] args) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8000/mock"))
                    .headers("header1", "value21", "header2", "value2")
                    .GET()
                    .build();

//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(new URI("http://localhost:8000/mock"))
//                    .headers("Content-Type", "text/plain;charset=UTF-8")
//                    .POST(HttpRequest.BodyPublishers.ofString("Sample request body"))
//                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Server Response headers = " +  response.headers().map());
            System.out.println("Server Response body = " +  response.body());

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
