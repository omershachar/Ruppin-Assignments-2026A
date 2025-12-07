import java.net.*;
import java.util.Random;

public class QuoteUDPServer {
    private static final int PORT = 8080;
    private static final String[] QUOTES = {
        "The only way to do great work is to love what you do. - Steve Jobs",
        "Innovation distinguishes between a leader and a follower. - Steve Jobs",
        "Life is what happens to you while you're busy making other plans. - John Lennon",
        "The future belongs to those who believe in the beauty of their dreams. - Eleanor Roosevelt",
        "It is during our darkest moments that we must focus to see the light. - Aristotle",
        "The way to get started is to quit talking and begin doing. - Walt Disney",
        "Don't let yesterday take up too much of today. - Will Rogers"
    };

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(PORT);
            System.out.println("Server is listening on port " + PORT);
            
            Random random = new Random();
            byte[] buffer = new byte[1024];
            
            while (true) {
                // Receive packet from client
                DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(requestPacket);
                
                // Get client address and port
                InetAddress clientAddress = requestPacket.getAddress();
                int clientPort = requestPacket.getPort();
                
                // Read the message from client
                String clientMessage = new String(requestPacket.getData(), 0, requestPacket.getLength());
                
                // Check if client wants to exit
                if (clientMessage.equals("exit")) {
                    System.out.println("Client requested to exit. Closing connection.");
                    break;
                }
                
                // Check if message is "GET"
                if (clientMessage.equals("GET")) {
                    System.out.println("Request received from client");
                    
                    // Select random quote
                    String quote = QUOTES[random.nextInt(QUOTES.length)];
                    
                    // Create response packet
                    byte[] responseData = quote.getBytes();
                    DatagramPacket responsePacket = new DatagramPacket(
                        responseData,
                        responseData.length,
                        clientAddress,
                        clientPort
                    );
                    
                    // Send quote back to client
                    socket.send(responsePacket);
                }
            }
            
            socket.close();
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

