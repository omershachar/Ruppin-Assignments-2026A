package simpleNetworking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(5500)) {
            System.out.println("Server is listening on port 5500");
            Socket socket = serverSocket.accept();
            System.out.println("new client connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} //end of class
