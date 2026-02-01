import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private List<Client> clientList;


    public ClientHandler(Socket clinetSocket) {
        this.clientSocket=clinetSocket;
    }
    public ClientHandler(Socket clinetSocket, List<Client> clientList) {
        this.clientSocket=clinetSocket;
        this.clientList=clientList;

    }

    public void run() {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
            String inputLine, outputLine;
            //talk with KnockKnockProtocol
            if (clientSocket.getLocalPort() == 4444) {
                KnockKnockProtocol kkp = new KnockKnockProtocol();
                outputLine = kkp.processInput(null);
                out.println(outputLine);

                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals("q")) break;
                    outputLine = kkp.processInput(inputLine);
                    out.println(outputLine);

                }
                //talk with RuppinRegistrationProtocol
            } else {
                RuppinRegistrationProtocol rrp=new RuppinRegistrationProtocol(clientList);
                outputLine=rrp.processInput(null);
                out.println(outputLine);

                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals("q")) break;
                    outputLine = rrp.processInput(inputLine);
                    out.println(outputLine);

                    //break from the loop and continue to close the clientSocket
                    //after the registration completed
                    if(outputLine.equals("Registration complete.") || outputLine.equals("Thank you, bye bye")
                    || outputLine.contains("Thanks, Your information has been updated")) break;


                }
            }



        } catch (IOException e) {
            System.out.println("Error handling client" + e.getMessage());
        }
        finally {
            try {
                clientSocket.close();
                System.out.println("Client disconnected: " + clientSocket.getPort());
            } catch (IOException e) {
                System.out.println("Error closing socket: " + clientSocket.getPort() + "\n" + e.getMessage());
            }

        }




    }


}
