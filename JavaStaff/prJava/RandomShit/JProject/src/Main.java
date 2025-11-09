public class Main {
    public static void main(String[] args) {
        System.out.println("HI!!");
        System.out.println("Check" + System.getProperty("java.version"));
        Bitch b1 = new Bitch();
        Bitch b2 = new Bitch("John", 12345);
        // b1.printBitch();
        // b2.printBitch();
        System.out.println(b1);
        System.out.println(b2);
        try {
            App.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
