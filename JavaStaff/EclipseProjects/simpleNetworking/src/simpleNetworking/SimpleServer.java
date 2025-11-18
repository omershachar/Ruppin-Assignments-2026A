package simpleNetworking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    public static void main(String[] args) {
        while(true) {
            try(ServerSocket serverSocket = new ServerSocket(5000)) {
                System.out.println("Server is listening on port 5000");
                Socket socket = serverSocket.accept();
                System.out.println("new client connected");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Stopped listening");
    }
} //end of class
