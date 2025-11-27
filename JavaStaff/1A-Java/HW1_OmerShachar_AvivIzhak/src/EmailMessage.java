
import java.util.ArrayList;

public class EmailMessage extends Message implements IDigital{


    private String subject;
    private ArrayList<File> attachments;


    public EmailMessage(String sender, String content, String subject, ArrayList<File> attachments) {
        super(sender, content);
        setSubject(subject);
        this.attachments = new ArrayList<File>(attachments);//crate a new arraylist with the same
                                                            // content of attachments
    }

    public EmailMessage(String sender, String content, String subject) {
        super(sender, content);
        attachments=new ArrayList<>();
        setSubject(subject);
    }


    public void setSubject(String subject)throws IllegalArgumentException {
        if (subject.trim().isEmpty()) {
            throw new IllegalArgumentException("subject cannot be null! we unable to create this message!");
        }
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public ArrayList<File> getAttachments() {
        return attachments;
    }

    public String toString() {
        return super.toString() + ", message subject: " + getSubject() + ", attachments:"
                + getAttachments().toString();
    }

    public String printCommunicationMethod() {
        return "Sent via Email Server";
    }

    public String generatePreview() {
        return "[Email] Subject: " +getSubject() + "| From: " +getSender();
    }

    public void addAttachment(File file) {
        getAttachments().add(file);
    }

    public void removeAttachment(File file)throws AttachmentException {
        if(!getAttachments().contains(file)) {
            throw new AttachmentException("File does not exist");
        }

        while(getAttachments().contains(file)) {
            getAttachments().remove(file);
        }

    }









}
