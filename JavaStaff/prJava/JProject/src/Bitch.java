public class Bitch {
    public String name;
    public int num;

    public Bitch() {
        name = "Bitch";
        num = 123;
    }

    public Bitch(String name, int num) {
        this.name = name;
        this.num = num;
    }

    public void printBitch() {
        System.out.println("name: " + name);
        System.out.println("num: " + num);
    }

    public String toString() {
        return "name: " + name + "\nnum: " + num;
    }
}
