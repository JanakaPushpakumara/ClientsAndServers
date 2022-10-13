package org.example;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SocketClient {

    private static int port = 8290;
    private static String host = "localhost";

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream objectoutputstream = null;
        ObjectInputStream objectinputstream = null;
        while(true){
            //establish socket connection to server
            socket = new Socket(host, port);
            //write to socket using ObjectOutputStream
            objectoutputstream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("ready to send request to Socket Server");
            //read the scan input and send
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = reader.readLine();
            // send the scanned input
            objectoutputstream.writeObject(line);
            //read the server response message
            objectinputstream = new ObjectInputStream(socket.getInputStream());
            String message = (String) objectinputstream.readObject();
            System.out.println("Message: " + message);
            //close resources
            objectinputstream.close();
            objectoutputstream.close();
        }
    }
}