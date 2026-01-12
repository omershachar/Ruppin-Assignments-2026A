public class Client {

    // Input fields
    private String businessName;
    private String businessNumber;
    private int itemType;  // 1=sunglasses, 2=belt, 3=scarf
    private int quantity;

    // Constructor
    public Client(String businessName, String businessNumber, int itemType, int quantity) {
        this.businessName = businessName;
        this.businessNumber = businessNumber;
        this.itemType = itemType;
        this.quantity = quantity;
    }


    // Getters and Setters
    
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    // Helper methods
    
    public void addQuantity(int additionalQuantity) {
        this.quantity += additionalQuantity;
    }

    public boolean isFiveDigits(String s) {
        return (s.length() == 5) && (isFiveDigits(s, 0));
    }

    public boolean isFiveDigits(String s, int i) {
        if (i == 5) {return true;}
        else return (character.isDigit(s.charAt(i))) && isFiveDigits(s, i+1);
    }

}
