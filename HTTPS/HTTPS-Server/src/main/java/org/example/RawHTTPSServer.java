package org.example;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RawHTTPSServer {

    private static final Logger logger = Logger.getLogger(RawHTTPSServer.class.getName());
    private static SSLServerSocket serverSocket;
    private static final int port = 8243;

    public static void main(String[] args) {
        try {
            initializeSSLServerSocket();
            startServer();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An unexpected error occurred", e);
        }
    }

    private static void initializeSSLServerSocket() throws Exception {
        // Load the keystore from the file
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (FileInputStream keyStoreStream = new FileInputStream("./HTTPS/HTTPS-Server/src/main/resources/wso2carbon.jks")) {
            keyStore.load(keyStoreStream, "wso2carbon".toCharArray());
        }

        // Initialize the KeyManagerFactory with the keystore
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, "wso2carbon".toCharArray());

        // Initialize the SSLContext with the KeyManagerFactory
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        // Create an SSLServerSocketFactory from the SSLContext
        SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
        serverSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
    }

    private static void startServer() {
        System.out.println("SSL Server Started!");

        while (true) {
            try (Socket client = serverSocket.accept()) {
                System.out.println("Accepting the client connection ...");
                handleClientRequest(client);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "An I/O error occurred", e);
            }
        }
    }

    private static void handleClientRequest(Socket client) throws IOException {
        // Read the request from the client
        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream());

        String line;
        while ((line = input.readLine()) != null) {
            System.out.println("Response : " + line);
            if (line.isEmpty()) {
                break;
            }
        }

        // Send the response to the client
        String content = "{\"Hello\" : \"World\"}";
        int contentLength = content.getBytes().length;

        out.print("HTTP/1.1 200 OK\r\n");
        out.print("Access-Control-Expose-Headers:\r\n");
        out.print("Access-Control-Allow-Origin: *\r\n");
        out.print("X-Correlation-ID: 12345678\r\n");
        out.print("Access-Control-Allow-Headers: authorization,Access-Control-Allow-Origin,Content-Type,SOAPAction,apikey,testKey,Authorization\r\n");
        out.print("Content-Length: " + contentLength + "\r\n");
        out.print("Content-Type: application/json\r\n");
        out.print("Date: Tue, 14 Dec 2021 08:15:17 GMT\r\n");
        out.print("Connection: Close\r\n");
        out.print("\r\n");
        out.print(content + "\r\n");
        out.flush();

        System.out.println("Closing server connection ....");

    }
}