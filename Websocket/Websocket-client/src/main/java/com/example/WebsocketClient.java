package com.example;

import org.java_websocket.client.WebSocketClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class WebsocketClient{
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String path = args[0];
        Properties prop = new Properties();
        File initialFile = new File(path);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(initialFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (inputStream != null) {
            try {
                prop.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("property file is not found");
        }
        String url = prop.getProperty("wssurl");
        String token = prop.getProperty("token");
        String KEYSTORE = prop.getProperty("truststorepath");
        String STOREPASSWORD = prop.getProperty("storepassword");
        String KEYPASSWORD = prop.getProperty("keypassword");
        Map<String, String> httpHeaders = new HashMap<String, String>();
        httpHeaders.put("Authorization", "Bearer " + token);
        WebSocketClient chatclient = new WebSocketChatClient(new URI(url), httpHeaders);
        String STORETYPE = "JKS";
        KeyStore ks = KeyStore.getInstance(STORETYPE);
        File kf = new File(KEYSTORE);
        ks.load(new FileInputStream(kf), STOREPASSWORD.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, KEYPASSWORD.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);
        SSLContext sslContext = null;
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        SSLSocketFactory factory = sslContext.getSocketFactory();
        chatclient.setSocketFactory(factory);
        chatclient.connectBlocking();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = reader.readLine();
            if (line.equals("close")) {
                chatclient.closeBlocking();
                continue;
            }
            if (line.equals("open")) {
                chatclient.reconnect();
                continue;
            }
            chatclient.send(line);
        }
    }
}
