import javax.swing.*;
public static void main(String[] args) {

    MyAnim anim = new MyAnim();
    JFrame frame = new JFrame("Dancing Letters");
    frame.setSize(800,600);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(anim);
    frame.setVisible(true);


}
