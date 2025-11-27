import java.util.Date;



public class BoardMessage extends Message {



    private PriorityType priority;
    private String messageSubject;

    public BoardMessage(String sender, String content, Date sendDate, PriorityType priority, String messageSubject) {
        super(sender,content,sendDate);
        setPriority(priority);
        setMessageSubject(messageSubject);

    }

    public BoardMessage(String sender, String content,String messageSubject) {
        super(sender,content);
        priority=PriorityType.REGULAR;
        setMessageSubject(messageSubject);

    }


    public void setPriority(PriorityType priority) {

        this.priority = priority;
    }


    public PriorityType getPriority() {
        return priority;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public String toString() {
        return super.toString() + ", Priority: " + priority + ", MessageSubject: " + messageSubject;
    }

    public String generatePreview() {
        String content15;
        //check if there is 15 letters in the content
        if(getContent().length()<=15) {
            content15 = getContent();
        }
        else {
            content15 = getContent().substring(0, 15);//getting the first 15 letters
        }

      return "[Board] "+ getSender() + ": " +content15 + "...";

    }

}
