import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Abstract base class representing a message in the messaging system.
 * Provides common functionality for all message types including sender,
 * content, date, and unique message ID generation.
 * 
 * @author Omer Shachar & Aviv itzak
 * @version 1.0
 */


public abstract class Message {
        
    private String sender;
    private String content;
    private Date sendDate;
    private final int messageID;
    private static int messageIDCount = 1;
    public final String DEFAULT_SENDER = "";
    public final String DEFAULT_CONTENT = "";
    
    /**
     * Constructs a Message with sender, content, and send date.
     * 
     * @param sender The sender's name (cannot be null or empty)
     * @param content The message content (cannot be null or empty)
     * @param sendDate The date the message was sent (cannot be null)
     * @throws IllegalArgumentException if sender or content is null or empty
     */
    public Message(String sender, String content, Date sendDate) {
        setSender(sender);
        setContent(content);
        setSendDate(sendDate);
        this.messageID = messageIDCount++;
    }

    // Constructors:
    public Message() {
        this.sender = DEFAULT_SENDER;
        this.content = DEFAULT_CONTENT;
        // this.sendDate = new Date(0,0,0); // Sets the date to the default date
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
