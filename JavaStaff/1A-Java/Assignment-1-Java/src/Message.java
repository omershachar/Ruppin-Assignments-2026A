import java.util.ArrayList;
import java.util.Date;
public  abstract class Message {

    private String sender;
    private String content;
    private Date sendDate;
    private int messageID;
    private static int messageIDCount=1;

    public Message(String sender, String content, Date sendDate) {

        setSender(sender);
        setContent(content);
        setSendDate(sendDate);
        messageID=messageIDCount++;
    }

    public Message(String sender, String content) {
        setSender(sender);
        setContent(content);
        sendDate = new Date();
        messageID=messageIDCount++;


    }

    //check if one of the word from the arraylist found in the message content
    public boolean find(ArrayList<String> list ) {
        for (String s : list) {
            if (content.contains(s)) {
                return true;
            }

        }
        return false;
    }

    public String toString()  {
        return "Sender is: " + sender + ", Content is: " + content + ", Date send: " + sendDate + ", Message ID: " + messageID;

    }

    //private method only accessible for this class(for the constructor)
    private void setSender(String sender) throws IllegalArgumentException
    {
        if (sender.trim().isEmpty()) {
            throw new IllegalArgumentException("Sender name cannot be null! we unable to create this message!");

        }
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    //private method only accessible for this class(for the constructor)
    private void setContent(String content) throws IllegalArgumentException
    {
        if (content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null! we unable to create this message!");
        }
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getSendDate() {

        return sendDate;
    }

    public int getMessageID() {
        return messageID;
    }

    public int getMessageLength() {
        return content.length();
    }



    public abstract String generatePreview();


}

