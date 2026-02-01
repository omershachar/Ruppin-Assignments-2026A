import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorTCPServer {
    public static void main(String[] args) throws IOException {


        //listening on port 9090 - close port auto after that
        try (ServerSocket serverSocket = new ServerSocket(9090))
        {

            System.out.println("Server listening on port 9090");

            //waiting for request to connect and return a socket to communicate when client incoming
            Socket clientSocket = serverSocket.accept();
            System.out.println("A new client connected");

            //getting the data from the client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String input;
            String operator;
            String result="";
            String[] splitInput;
            float num1;
            float num2;

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            while ((input = in.readLine()) != null) {

                if(input.equals("close")) {
                    System.out.println("Client requested to close connection\nClient disconnected");
                    out.println("Client closed");
                }
                //split the input into words
                splitInput = input.split(" ");

                //check if there is more 3 word(invalid)
                if (!Check(splitInput))
                {
                    result="Error: Invalid expression!";
                }
                else {
                    System.out.println("The math problem we got is: " + input);

                    num1=Integer.parseInt(splitInput[0]);
                    num2=Integer.parseInt(splitInput[2]);
                    operator=splitInput[1];

                    //identify the operator and do the math
                    switch (operator) {
                        case "+":
                            result= Float.toString(num1 + num2);
                            break;
                        case "-":
                            result= Float.toString(num1 - num2);
                            break;
                        case "*":
                            result= Float.toString(num1 * num2);
                            break;
                        case "/":
                            if (num2 == 0) {
                               result="Error: Division by zero!";
                            }
                            else {
                                result= Float.toString(num1 / num2);
                            }
                            break;

                        default:
                            result="Error: Invalid expression!";

                    }

                }


                out.println(result);

            }


        }
        catch (IOException e) {
            e.printStackTrace();
        }




    }

    //validate input
    public static boolean Check(String[] splitInput ) {

        //check length
        if (splitInput.length != 3) {
            return false;
        }

        //check if digit
        if (!Character.isDigit(splitInput[0].charAt(0)) ||
                !Character.isDigit(splitInput[2].charAt(0))) {

            return false;
        }
        //check if valid operator
        if (!splitInput[1].equals("+") && !splitInput[1].equals("-") &&
                !splitInput[1].equals("*") && !splitInput[1].equals("/")) {

            return false;
        }

        return true;


    }





}
