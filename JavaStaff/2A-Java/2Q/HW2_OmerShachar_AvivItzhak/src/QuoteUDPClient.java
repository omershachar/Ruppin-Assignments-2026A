import java.net.*;
import java.util.Scanner;

public class QuoteUDPClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Client connected to server at " + SERVER_HOST + ":" + SERVER_PORT);
            
            while (true) {
                System.out.print("Enter 'GET' to receive a quote or 'exit' to quit: ");
                String userInput = scanner.nextLine().trim();
                
                // Check if user wants to exit
                if (userInput.equalsIgnoreCase("exit")) {
                    // Send exit message to server
                    byte[] exitData = "exit".getBytes();
                    DatagramPacket exitPacket = new DatagramPacket(
                        exitData,
                        exitData.length,
                        serverAddress,
                        SERVER_PORT
                    );
                    socket.send(exitPacket);
                    System.out.println("Exiting...");
                    break;
                }
                
                // If user entered "GET", send request to server
                if (userInput.equalsIgnoreCase("GET")) {
                    // Create and send request packet
                    byte[] requestData = "GET".getBytes();
                    DatagramPacket requestPacket = new DatagramPacket(
                        requestData,
                        requestData.length,
                        serverAddress,
                        SERVER_PORT
                    );
                    socket.send(requestPacket);
                    
                    // Receive response from server
                    byte[] responseBuffer = new byte[1024];
                    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                    socket.receive(responsePacket);
                    
                    // Extract and print the quote
                    String quote = new String(responsePacket.getData(), 0, responsePacket.getLength());
                    System.out.println("Quote received: " + quote);
                } else {
                    System.out.println("Invalid input. Please enter 'GET' or 'exit'.");
                }
            }
            
            scanner.close();
            socket.close();
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

