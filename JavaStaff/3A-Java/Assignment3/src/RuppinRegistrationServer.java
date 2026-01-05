import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RuppinRegistrationServer {
    public static void main(String[] args) throws IOException {

        //lock the list for safe run of parallel client
        List<Client> clientList = Collections.synchronizedList(new ArrayList<>());

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


    public static void saveToCSV(List<Client> clientList) {
        synchronized (clientList) {

            String date = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmm"));
            String fileName = "backup_" + date + ".csv";

            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

                for (Client i : clientList) {
                    writer.println(
                            i.getUserName() + "," +
                                    i.getPassword() + "," +
                                    i.getAcademicStatus() + "," +
                                    i.getYearsOfStd()
                    );
                }

                System.out.println("Backup created: " + fileName);

            } catch (IOException e) {
                System.err.println("Backup error: " + e.getMessage());
            }
        }
    }

    }

