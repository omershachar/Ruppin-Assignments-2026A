import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GeneralClient {


        public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);
            int chosenPort;

            System.out.println("Choose server (Enter only digit):" + "\n" + "KnockKnock Server- 4444 "
                    + "\n" +"Ruppin Registration Server -4445");

            while (true) {
                if (scanner.hasNextInt()) {
                    chosenPort = scanner.nextInt();
                    if (chosenPort == 4444 || chosenPort == 4445) {
                        break;
                    }
                }
                else
                {
                    scanner.next();
                }
                System.out.println("Invalid input!" + "\n" + "Enter 4444 or 4445: ");
            }

            try (Socket socket = new Socket("127.0.0.1", chosenPort);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))
            ) {

                String fromTheServer;
                String fromTheUser;

                while ((fromTheServer = in.readLine()) != null) {
                    System.out.println("Server: " + fromTheServer);


                    if (fromTheServer.equals("Registration complete.") ||
                            fromTheServer.equals("Thank you, bye bye") ||
                            fromTheServer.contains("Thanks, Your information has been updated")||
                            fromTheServer.equalsIgnoreCase("Bye.")) {
                        break;
                    }

                    fromTheUser = userInput.readLine();
                    if (fromTheUser != null) {
                        out.println(fromTheUser);
                    }
                }

            } catch (IOException e) {
                System.out.println("Connection error: " + e.getMessage());
            }
        }
    }

