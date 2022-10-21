package org.example;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class HTTPSClient {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, KeyManagementException {
        final String endpoint = "https://localhost:8000/mock";
        URL url;
        try {
            url = new URL(endpoint);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            SSLContext sslContext = SSLContext.getInstance("SSL");
            //sslContext.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
            char[] password = "wso2carbon".toCharArray();
            KeyStore keyStore = KeyStore.getInstance("JKS");
            FileInputStream fileInputStream = new FileInputStream("./HTTPS/HTTPS-Client/src/main/resources/client-truststore.jks");
            keyStore.load(fileInputStream, password);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            connection.setSSLSocketFactory(sslContext.getSocketFactory());
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(1000);
            printContent(connection);
        } catch (IOException urlException) {
            urlException.printStackTrace();
        }
    }

    private static void printContent(HttpsURLConnection connection) {
        if (connection != null) {
            try {
                System.out.println("\nResponse code = " + connection.getResponseCode());
                System.out.print("Response body = ");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String input;
                while ((input = bufferedReader.readLine()) != null) {
                    System.out.println(input);
                }
                bufferedReader.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
