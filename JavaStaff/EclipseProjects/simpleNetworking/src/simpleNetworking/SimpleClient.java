package simpleNetworking;

import java.io.IOException;
import java.net.Socket;

public class SimpleClient {
 public static void main(String[] args) {
     try (Socket socket = new Socket("localhost", 5000)) {
         System.out.println("Connected to the server");
     } catch (IOException e) {
         e.printStackTrace();
     }
 }
} //end of class
