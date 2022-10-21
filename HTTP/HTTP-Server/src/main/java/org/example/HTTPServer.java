package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HTTPServer {

    static int port = 8000;
    static String context = "/mock";
    // final URL = http://localhost:8000/mock
    static int responseCode = 200;


    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext(context, new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    private static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

                String response = "{\"Hello\":\"World\"}";
                // Set content length header
                httpExchange.sendResponseHeaders(responseCode, response.length());
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
        }
    }
}