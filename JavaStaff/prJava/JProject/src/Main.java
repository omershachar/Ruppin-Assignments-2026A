public class Main {
    public static void main(String[] args) {
        System.out.println("HI!!");
        System.out.println("Check" + System.getProperty("java.version"));

        // SImpleServer.main(args);
        // SimpleClient.main(args);
        try {
            SImpleServer.main(args);
            SimpleClient.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
