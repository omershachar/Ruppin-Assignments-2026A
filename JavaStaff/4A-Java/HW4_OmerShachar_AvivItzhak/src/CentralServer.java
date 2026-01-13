import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CentralServer {

    private static final int PORT = 9999;
    private static List<Client> clientList = new ArrayList<>();
    private ServerSocket serverSocket;

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Central Server started on port " + PORT);
            System.out.println("Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getRemoteSocketAddress());
                
                // Create a new thread to handle this client + starting it
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientList);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.err.println("Error in server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeServer();
        }
    }

    private void closeServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Server closed.");
            }
        } catch (IOException e) {
            System.err.println("Error closing server: " + e.getMessage());
        }
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public static void main(String[] args) {
        CentralServer server = new CentralServer();
        server.start();
    }
}
