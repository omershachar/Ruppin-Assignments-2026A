// package JavaPr.L1;

public class Validator {
    public boolean isNum(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}