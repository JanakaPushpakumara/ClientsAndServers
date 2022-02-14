package com.example;
import java.net.URI;
import java.util.Map;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

class WebSocketChatClient extends WebSocketClient {
    public WebSocketChatClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected");
    }

    public void onMessage(String message) {
        System.out.println("got: " + message);
    }

    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected");
    }

    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}
