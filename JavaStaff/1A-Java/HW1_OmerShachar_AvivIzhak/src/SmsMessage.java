
import java.io.IOException;
import java.util.Date;

public class SmsMessage extends Message implements IDigital{

    private final int smsMaxLength = 200;

    public SmsMessage(String sender, String content)throws SmsLengthException {
        super(sender, content);

        if(content.length() > smsMaxLength){
            throw new SmsLengthException("Message content length exceeds sms max length!");
        }

    }

    public int getSmsMaxLength() {
        return smsMaxLength;
    }
    
    public String toString() {
        return super.toString();
    }

    public String printCommunicationMethod() {
        return "Sent via Mobile Carrier";
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

        return "[SMS] | From: " +getSender() + " Preview: " +content15;

    }
}