import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CalculatorTCPClient {


    public static void main(String[] args) throws IOException {

        try (Socket clinetSocket = new Socket("localhost", 9090)) {

            //get the expression from the user
            String expression="";
            PrintWriter out = new PrintWriter(clinetSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clinetSocket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            while (!expression.equals("close")){

                System.out.println("Enter expression (num op num) or close to exit: ");

                expression=scanner.nextLine();

                //send the expression
                out.println(expression);

                //get the answer
                System.out.println(in.readLine());



            }


        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
