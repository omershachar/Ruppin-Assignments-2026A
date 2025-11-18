import java.util.ArrayList;

public abstract class Message {
    // Fields:
    public String sender;
    public String content;
    public Date sendDate; // Using Date class that i wrote in the open university...

    public final String DEFAULT_SENDER = "";
    public final String DEFAULT_CONTENT = "";

    // Constructors:
    public Message() {
        this.sender = DEFAULT_SENDER;
        this.content = DEFAULT_CONTENT;
        this.sendDate = new Date(0,0,0); // Sets the date to the default date
    }

    public Message(String sender, String content, Date sendDate) {
        setSender(sender);
        setContent(content);
        setSendDate(sendDate);
    }

    // Getters:
    public String getSender() {
        return sender;
    }
    
    public String getContent() {
        return content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    // Setters:
    public void setSender(String sender) {
        this.sender = isSenderValid(sender) ? sender : DEFAULT_SENDER;
    }
    
    public void setContent(String content) {
        this.content = isSenderValid(content) ? content : DEFAULT_CONTENT;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    // Methods:
    public String toString() {
        return "";
    }

    public Boolean find(ArrayList<String> list) {
        return false;
    }

    public String generatePreview() {
        return "";
    }

    // Helpers:
    public boolean isSenderValid(String sender) {
        return false;
    }
    public boolean isContentValid(String content) {
        return false;
    }
}
