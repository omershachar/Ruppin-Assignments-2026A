import java.util.*;
import java.text.*;

public class Main {

    public static void main(String[]args) {
        
    ArrayList<Message> arrayList = new ArrayList<Message>();

    //create two messages manually
    EmailMessage emailMessage= new EmailMessage
            ("Milki","Hello world, its a beautiful day","Hello");

    BoardMessage boardMessage = new BoardMessage
            ("Yoni","The weekly meeting will take place tomorrow at 10 AM","Weekly meeting");

    //add the new messages to the array list
    arrayList.add(emailMessage);
    arrayList.add(boardMessage);
    Scanner input = new Scanner(System.in);

    System.out.println("Welcome!\nWhat do you want to do today ?");
    while (true){
         System.out.println("\n1. Add a new message\n2. Delete a message\n3. Print all available messages\n" +
                "4.Display the number of messages whose content contains words\n5.Print only the digital messages\n" +
                "6. Show total number of messages\n\n\n");


        String choice = input.nextLine();


        switch (choice) {
            case "1":
                addNewMessage(arrayList);
                break;

            case "2":
                deleteMessage(arrayList);
                break;

            case "3":
                printAllMessages(arrayList);
                break;

            case "4":
                onlyContentAvaliable(arrayList);

                break;
            case "5":
                onlyDigital(arrayList);

                break;
            case "6":
                summary(arrayList);

                break;

            default: System.out.println("Invalid choice!");
        }

    }




  }
  //adding messages by user choice
public static void addNewMessage( ArrayList<Message> arrayList) {
    System.out.println("Choose the message you want to send:\n" +
            "1.Board Message\n2.EmailMessage\n3.SMS Message");

    Scanner input = new Scanner(System.in);
    String choice = input.nextLine();
    
    switch(choice){
    case "1":
        System.out.println("Enter sender name: ");
        String sender = input.nextLine();
        //input.nextLine();

        System.out.println("Enter content: ");
        String content = input.nextLine();
        //input.nextLine();

        System.out.println("Enter date  dd/MM/yyyy: ");
        String date = input.nextLine();
        //input.nextLine();

        //convert the user input to Date object
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date sendDate=null;
        try{
            sendDate = sdf.parse(date);
        }
        catch(ParseException e){
            System.out.println("Invalid date format!- today's date was set");
            sendDate = new Date();
        }

        PriorityType chosenPriority=null;
        try {
            System.out.println("Choose Priority:\n1-Regular\n2-Medium\n3-Urgent ");
            int priority = input.nextInt();
            input.nextLine();

            //convert the user choice to ENUM
            if(priority == 1){
                chosenPriority = PriorityType.REGULAR;
            }
            else if(priority == 2){
                chosenPriority = PriorityType.MEDIUM;
            }
            else if(priority == 3){
                chosenPriority = PriorityType.URGENT;
            }
            else{
                System.out.println("Invalid number!\nDefault priority was set (REGULAR)");
                chosenPriority = PriorityType.REGULAR;
                input.nextLine();
            }

        }
        catch(InputMismatchException e){
            System.out.println("Invalid input only numbers allowed!\nDefault priority was set (REGULAR)");
            chosenPriority = PriorityType.REGULAR;
            input.nextLine();
        }

        System.out.println("Enter message subject: ");
        String subject = input.nextLine();

        try{
             BoardMessage boardMessage= new BoardMessage(sender,content,sendDate,chosenPriority,subject);
             arrayList.add(boardMessage);
             System.out.println(" Board Message added successfully!\n");

        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

            break;
        case "2":
            System.out.println("Enter sender name: ");
            String sender2 = input.nextLine();
            //input.nextLine();

            System.out.println("Enter content: ");
            String content2 = input.nextLine();
            //input.nextLine();

            System.out.println("Enter subject: ");
            String subject2 = input.nextLine();
            //input.nextLine();
            
            //option to add files to the email
            System.out.println("Do you want to add files to your email (y/n): ");
            String again;
            if(input.nextLine().equals("y")){
                again = "y";
                ArrayList<File> files = new ArrayList<File>();
                while (again.equals("y")) {


                    System.out.println("Enter file name: ");
                    String fileName = input.nextLine();
                    //input.nextLine();

                    System.out.println("Enter file type: ");
                    String fileType = input.nextLine();
                    //input.nextLine();

                    files.add(new File(fileName, fileType));
                    System.out.println("File added successfully!\n");

                    System.out.println("Add another file? (y/n): ");
                    again = input.nextLine();
                }
                
                try{
                    EmailMessage emailMessage = new EmailMessage(sender2,content2,subject2,files);
                    arrayList.add(emailMessage);
                    System.out.println("Email Message (with files) added successfully!\n");
                    return;
                }
                catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }


            }

            try{
                EmailMessage emailMessage = new EmailMessage(sender2,content2,subject2);
                arrayList.add(emailMessage);
                System.out.println("Email Message (without files) added successfully!\n");
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

            break;
            case "3":
                System.out.println("Enter sender name: ");
                String sender3 = input.nextLine();
                //input.nextLine();

                System.out.println("Enter content: ");
                String content3 = input.nextLine();
                //input.nextLine();

                try{
                    SmsMessage smsMessage = new SmsMessage(sender3,content3);
                    arrayList.add(smsMessage);
                    System.out.println("SMS Message added successfully!\n");
                }
                catch (SmsLengthException | IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }


                break;
                
}


}

//delete messages by user choice
public static void deleteMessage(ArrayList<Message> arrayList){
    Scanner input = new Scanner(System.in);

    if(arrayList.isEmpty()){
        System.out.println("There are no messages to delete!\n");
        return;
    }

    //shows all the messages preview
   for(int i=0 ;i<arrayList.size(); i++){
       System.out.println("Message number " + (i+1) + " " + arrayList.get(i).generatePreview());

   }
   
    System.out.println("\n");
    System.out.println("Chose the  number message you want to delete:\n");

    int choice = input.nextInt();
    input.nextLine();

    if (choice<arrayList.size()-1) {
        arrayList.remove(arrayList.get(choice-1));
    }
    else{
        System.out.println("Invalid choice!\n");
    }
    
    

}

//printing all the messages data
public static void printAllMessages(ArrayList<Message> arrayList){
    System.out.println("\nAll messages will now be displayed!\n");

    for(Message message: arrayList){
        System.out.println(message);
    }
}

//Print only messages with available content
public static void onlyContentAvaliable(ArrayList<Message> arrayList){
    int count=0;
    for(Message message: arrayList){
        if (!message.getContent().trim().isEmpty()){
            count++;
        }
    }

    System.out.println("The number of messages that contains words are " + count);
}


//printing only the digital messages
public static void onlyDigital(ArrayList<Message> arrayList){
    for(Message message: arrayList){
        if (message instanceof IDigital) {
            System.out.println(message);
        }
    }
}

//display the available messages by type
public static void summary(ArrayList<Message> arrayList){
    int bMessage=0;
    int eMessage=0;
    int sMessage=0;

    for(Message message: arrayList){
        if(message instanceof BoardMessage){
            bMessage++;
        }
        if(message instanceof EmailMessage){
            eMessage++;
        }
        if(message instanceof SmsMessage){
            sMessage++;
        }
    }
    System.out.println("Message Summary:\nNumber of board messages: " + bMessage);
    System.out.println("Number of email messages: " + eMessage);
    System.out.println("Number of sms messages: " + sMessage);
}

}