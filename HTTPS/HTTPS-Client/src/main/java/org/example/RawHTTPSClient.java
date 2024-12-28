package org.example;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.InetAddress;
import java.security.KeyStore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RawHTTPSClient {

    private static final Logger logger = Logger.getLogger(RawHTTPSClient.class.getName());

    private static final int port = 8243;
    private static final String host = "localhost";

    public static void main(String[] args) {
        try (SSLSocket sslSocket = createSSLSocket()) {
            sendRequest(sslSocket);
            readResponse(sslSocket);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An unexpected error occurred", e);
        }
    }

    private static SSLSocket createSSLSocket() throws Exception {
        // Load the truststore from the file
        KeyStore trustStore = KeyStore.getInstance("JKS");
        try (InputStream trustStoreStream = new FileInputStream("./HTTPS/HTTPS-Server/src/main/resources/client-truststore.jks")) {
            trustStore.load(trustStoreStream, "wso2carbon".toCharArray());
        }

        // Initialize the TrustManagerFactory with the truststore
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        // Initialize the SSLContext with the TrustManagerFactory
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        // Create an SSLSocketFactory from the SSLContext
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        // Create and return an SSLSocket
        InetAddress serverAddress = InetAddress.getByName(host);
        SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(serverAddress, port);
        sslSocket.startHandshake();
        return sslSocket;
    }

    private static void sendRequest(SSLSocket sslSocket) throws IOException {
        // Send a request to the server
        PrintWriter out = new PrintWriter(sslSocket.getOutputStream(), true);
        out.println("GET /context/resource HTTP/1.1");

        out.println("Host: " + host);
        out.println("Connection: Close");
        out.println();
        out.flush();
    }

    private static void readResponse(SSLSocket sslSocket) throws IOException {
        // Read the response from the server
        BufferedReader in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }
}