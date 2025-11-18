public class BoardMessage extends Message{
    PriorityType priority;
    String sender, content;

    public BoardMessage(String sender, PriorityType priority) {
        this.sender = sender;
        this.priority = priority;
    }
    
    public BoardMessage() {        
        this.sender = "";
        this.priority = PriorityType.REGULAR;
    }

    public String toString() {
        return "";
    }
}

enum PriorityType {
    REGULAR, URGENT;
}
