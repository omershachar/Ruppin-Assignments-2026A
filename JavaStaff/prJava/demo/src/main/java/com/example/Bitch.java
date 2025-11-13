package com.example;

public class Bitch {
    private int num;
    private String name;

    public Bitch(int num, String name) {
        setName(name);
        setNum(num);
        printBitch();
    }
    public Bitch() {
        new Bitch(0, "Bitch");
    }
    
    public void setNum(int num) {
        this.num = num;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getNum() {
        return this.num;
    }
    public String getName() {
        return this.name;
    }
    public void printBitch() {
        System.out.println("Bitch name: " + getName() + "\tBitch num: " + getNum());
        System.out.println("\\ (O_0) / ");
    }
}
