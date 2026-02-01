import java.util.List;

public class RuppinRegistrationProtocol {
private int state=1;
private int stateExUser=1;
private boolean isExUser =false;

List<Client> clientList;
Client client;
Client temp;
Client refToRealClient;

public  RuppinRegistrationProtocol(List<Client> clientList) {
    this.clientList=clientList;
}


    public String processInput(String theInput){
        //String theOutput=null;
        char yearOfStd;

        if (isExUser) {
            return existingUser(theInput);
        }

        switch (state) {
            case 1:
                state++;
                    return "Do you want to register? (y/n): ";
            case 2:
                if (theInput.equalsIgnoreCase("y")) {
                    state++;//equal 3
                    return "Enter a username: ";
                } else if (theInput.equalsIgnoreCase("n")) {
                    isExUser=true;
                    return  existingUser(theInput); //go to the second session scenario
                } else {
                    return  "invalid selection!, Do you want to register? (y/n): ";
                }
            case(3):
                client = new Client(theInput);
                if (clientList.contains(client)) {
                    return "Checking name... Name not OK. Username exists. Choose a different name: ";
                } else {
                    state++; //equal 4
                    return  "Checking name... OK!. Enter a strong password: ";

                }
            case (4):
                if (client.checkPassword(theInput)) {
                    client.setPassword(theInput);
                    state++; //equal 5
                    return "Password accepted!, What is your academic status? (student/teacher/other)";
                }
                else
                    return "Password does not meet the requirements!, try again";

            case(5):
                if (theInput.equalsIgnoreCase("student") || theInput.equalsIgnoreCase("teacher")
                        || theInput.equalsIgnoreCase("other")) {
                    client.setAcademicStatus(theInput);
                    state++; //equal 6
                    return "How many years have you been at Ruppin?";
                }
                else
                    return "Invalid status";

            case (6):
                yearOfStd = theInput.charAt(0);
                if (theInput.length() == 1 && Character.isDigit(yearOfStd)) {
                    state = 1;
                    client.setYearsOfStd(yearOfStd);
                    //lock that block, prevent multi client insertion to the list
                    synchronized (clientList) {
                        if (clientList.contains(client)) {
                            state = 3; //go back to the user picking name stage
                            return "Username was taken during registration. Please choose a different username: ";
                        } else {
                            clientList.add(client); // after we're done configure the client fields we can add him to the list
                            if (clientList.size() % 3 == 0) {
                                RuppinRegistrationServer.saveToCSV(clientList);

                            }
                            return "Registration complete.";
                        }
                    }


                } else {
                    return "invalid input, try again";
                }


        }

        // fallback – should not happen
        return "Invalid input. Please try again.";
    }


    private String existingUser(String theInput) {
       switch (stateExUser) {
           case (1):
               stateExUser++; //equal 2
               return "Username: ";
           case (2):
               temp = new Client(theInput);
               if (clientList.contains(temp)) {
                   stateExUser++; //equal 3
                   return "Password: ";
               } else {
                   return "Incorrect Username: ";
               }
           case (3):
                refToRealClient = clientList.get(clientList.indexOf(temp));
               if (theInput.equals(refToRealClient.getPassword())) {
                   stateExUser++; //equal 4
                   return "Welcome back " + refToRealClient.getUserName() + ", Last time you defined yourself as " + refToRealClient.getAcademicStatus()
                           + " for " + refToRealClient.getYearsOfStd() + " years, Do you want to update your information? (yes/no) ";
               } else {
                   return "Incorrect Password: ";
               }

           case (4):
               if (theInput.equals("no")) {
                   return "Thank you, bye bye";
               } else if (theInput.equals("yes")) {
                   stateExUser++; //equal 5
                   return "Do you want to change your username? (yes/no)";

               } else {
                   return "Invalid input, Do you want to update your information? (yes/no)";
               }

           case (5):
               if (theInput.equals("yes")) {
                   stateExUser++; // equal 6
                   return "Enter new username: ";

               } else if (theInput.equals("no")) {
                   stateExUser = 7;
                   return "Do you want to change your password? (yes/no)";

               } else {
                   return "Invalid input!. Do you want to change your username? (yes/no)";
               }

           case (6):
               refToRealClient.setUserName(theInput);
               stateExUser++; // equal 7
               return "Username updated successfully. Do you want to change your password? (yes/no)";


           case (7):
               if (theInput.equals("yes")) {
                   stateExUser++; // equal 8
                   return "Enter new password:";
               } else if (theInput.equals("no")) {
                   stateExUser = 9;
                   return "Do you want to update your years of study? (yes/no)";
               } else {
                   return "Invalid input!. Do you want to change your password? (yes/no)";
               }


           case (8):
               if (refToRealClient.checkPassword(theInput)) {
                   refToRealClient.setPassword(theInput);
                   stateExUser++; // equal 9
                   return "Password updated successfully. Do you want to update your years of study? (yes/no)";
               } else {
                   return "Password does not meet the requirements, try again";
               }

           case (9):
               if (theInput.equals("yes")) {
                   stateExUser++; // equal 10
                   return "Enter number of years:";
               } else if (theInput.equals("no")) {
                   return "Thanks, Your information has been updated.";
               } else {
                   return "Invalid input!. Do you want to update your years of study? (yes/no)";
               }

           case (10):
               if (theInput.length() == 1 && Character.isDigit(theInput.charAt(0))) {
                   refToRealClient.setYearsOfStd(theInput.charAt(0));
                   return "Years of study updated successfully. Thanks, Your information has been updated.";
               } else {
                   return "Invalid input, Enter number of years:";
               }


       }

        // fallback – should not happen
        return "Invalid input. Please try again.";
    }


}
