package com.example;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class WebSocketChatServer extends WebSocketServer {
    public WebSocketChatServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Welcome to the server!");
        broadcast("new connection: " + handshake
                .getResourceDescriptor());
        System.out.println(conn
                .getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }

    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        broadcast(conn + " has left the room!");
        System.out.println(conn + " has left the room!");
    }

    public void onMessage(WebSocket conn, String message) {
        broadcast(message);
        System.out.println(conn + ": " + message);
    }

    public void onMessage(WebSocket conn, ByteBuffer message) {
        broadcast(message.array());
        System.out.println(conn + ": " + message);
    }

    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null)
            conn.close();
    }

    public void onStart() {
        System.out.println("Server started! on wss://localhost:8090");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }
}
