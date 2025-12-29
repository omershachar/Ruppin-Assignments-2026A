import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RuppinRegistrationServer {
    public static void main(String[] args) throws IOException {

        List<Client> clientList= new ArrayList<>();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4445);
            System.out.println("Server listening on port 4445");

            while (true) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket,clientList);
                    clientHandler.start();

                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }

            }
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port: 4445.");
            System.exit(1);
        }

        finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();

                } catch (IOException e) {
                    System.out.println("Error closing server socket" + e.getMessage());

                }
            }
        }

    }
}
