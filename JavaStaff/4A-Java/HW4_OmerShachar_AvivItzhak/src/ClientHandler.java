import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private List<Client> clientList;
    
    private static final String DISCONNECT_MESSAGE = "DISCONNECT";
    private static final String DELIMITER = "|";

    public ClientHandler(Socket clientSocket, List<Client> clientList) {
        this.clientSocket = clientSocket;
        this.clientList = clientList;
    }

    @Override
    public void run() {
        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // Check for disconnect message
                if (inputLine.equals(DISCONNECT_MESSAGE)) {
                    System.out.println("Client " + clientSocket.getRemoteSocketAddress() + " disconnected.");
                    break;
                }

                // Process the order request
                String response = processOrder(inputLine);
                out.println(response);

                // If disconnect was requested in response, break
                if (response.startsWith("DISCONNECT")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client " + clientSocket.getRemoteSocketAddress() + ": " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    
    private String processOrder(String message) {
        // Parse the message
        String[] parts = message.split("\\" + DELIMITER);
        
        // Validate: Check if all fields are present
        if (parts.length != 4) {
            return "200|Missing data: All fields are required";
        }

        String businessName = parts[0].trim();
        String businessNumber = parts[1].trim();
        String itemTypeStr = parts[2].trim();
        String quantityStr = parts[3].trim();

        // Check for empty fields
        if (businessName.isEmpty() || businessNumber.isEmpty() || 
            itemTypeStr.isEmpty() || quantityStr.isEmpty()) {
            return "200|Missing data: All fields must be filled";
        }

        // Validate business number format (5 digits)
        if (!businessNumber.matches("\\d{5}")) {
            return "202|Invalid business number format: Must be exactly 5 digits";
        }

        // Validate item type
        int itemType;
        try {
            itemType = Integer.parseInt(itemTypeStr);
            if (itemType < 1 || itemType > 3) {
                return "202|Invalid item type: Must be 1 (sunglasses), 2 (belt), or 3 (scarf)";
            }
        } catch (NumberFormatException e) {
            return "202|Invalid item type format";
        }

        // Validate quantity
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                return "202|Invalid quantity: Must be greater than 0";
            }
        } catch (NumberFormatException e) {
            return "202|Invalid quantity format";
        }

        // Business logic: Add or update client
        // Synchronize on the list to ensure thread-safe operations
        synchronized (clientList) {
            Client existingClient = findClientByBusinessNumber(businessNumber);
            
            if (existingClient != null) {
                // Client exists - verify business name matches
                if (!existingClient.matchesBusinessName(businessName)) {
                    return "201|Business name mismatch: Business number exists with different name";
                }
                
                // Name matches - update quantity
                existingClient.addQuantity(quantity);
                System.out.println("Updated client: " + existingClient);
                return "100|Order processed successfully. Quantity updated.";
            } else {
                // New client - add to list
                Client newClient = new Client(businessName, businessNumber, itemType, quantity);
                clientList.add(newClient);
                System.out.println("Added new client: " + newClient);
                return "100|Order processed successfully. New client added.";
            }
        }
    }

    /**
     * Finds a client by business number.
     * Must be called within a synchronized block.
     */
    private Client findClientByBusinessNumber(String businessNumber) {
        for (Client client : clientList) {
            if (client.matchesByBusinessNumber(businessNumber)) {
                return client;
            }
        }
        return null;
    }
}
