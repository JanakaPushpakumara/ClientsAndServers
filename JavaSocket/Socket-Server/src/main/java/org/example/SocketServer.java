package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 8290;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //create the socket server object
        server = new ServerSocket(port);
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Ready to receive the request from the socket client");
            //creating socket and waiting for client connection
            Socket socket = server.accept();
            //read from socket to ObjectInputStream object
            ObjectInputStream objectinputstream = new ObjectInputStream(socket.getInputStream());
            //convert ObjectInputStream object to String
            String message = (String) objectinputstream.readObject();
            System.out.println("Message Received: " + message);
            //create ObjectOutputStream object
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(socket.getOutputStream());
            //write object to Socket
            objectoutputstream.writeObject("Server echo : "+message);
            //close resources
            objectinputstream.close();
            objectoutputstream.close();
            socket.close();
            //terminate the server if client sends exit request
            if(message.equalsIgnoreCase("exit"))
                break;
        }
        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();
    }
}