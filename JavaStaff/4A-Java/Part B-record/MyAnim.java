import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyAnim extends Canvas implements ActionListener {

   String[] letters = {"I", "S"};
   Polygon[] polygons = new Polygon[2];
   String[] parts;
   int xPoint, yPoint;
   int count = 0;
   int velX = 3, velX2 = 3;
   int velY = 3, velY2 = 3;
   int x = 0, x2 = 0;
   int y = 0, y2 = 0;
   Color polIColor = Color.blue;
   Color polSColor = Color.CYAN;
   double rotate = 0;
   double scaleX = 1, scaleX2 = 1;
   double scaleY = 1, scaleY2 = 1;


   Timer timer = new Timer(30, this);

   public MyAnim() {

      //loop over the array for reading the txt files
      for (String letter : letters) {
         File file = new File("Letters/" + letter + ".txt");
         polygons[count] = new Polygon(); // create new pol and insert it to the arr

         try (Scanner scanner = new Scanner(file)) {
            //extract and add to the pol the x,y points from the file
            while (scanner.hasNextLine()) {
               String data = scanner.nextLine();
               parts = data.split(",");
               xPoint = Integer.parseInt(parts[0]);
               yPoint = Integer.parseInt(parts[1]);
               polygons[count].addPoint(xPoint, yPoint);
            }
            count++;
         } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
         }

      }
      timer.start(); //start the timer


   }


   public void paint(Graphics g) {
      super.paint(g);
      Graphics2D g2 = (Graphics2D) g;

      //letter I - properties and drawing
      g2.translate(x, y);
      g2.translate(323,157);// move the origin to the letter's center.
      g2.scale(scaleX, scaleY); //clean scale around (0,0)
      g2.rotate(rotate); // clean rotate
      g2.translate(-323,-157); // move the origin back to the original coordinate system

      g2.setColor(polIColor);
      g2.fillPolygon(polygons[0]);
      g2.setColor(Color.BLACK);
      g2.drawPolygon(polygons[0]);

      g2.setTransform(new AffineTransform()); //reset g2 properties

      //letter S - properties and drawing
      g2.translate(x2, y2);
      g2.translate(328, 443);
      g2.scale(scaleX2, scaleY2);
      g2.rotate(rotate);
      g2.translate(-328, -443);

      g2.setColor(polSColor);
      g2.fillPolygon(polygons[1]);
      g2.setColor(Color.BLACK);
      g2.drawPolygon(polygons[1]);
      g2.setTransform(new AffineTransform());


   }

   //when the timer tick- do
   public void actionPerformed(ActionEvent e) {
      rotate += Math.toRadians(3);//every tick rotate by 3 degree

      //move position by change letters coordinate
      x += velX;
      y += velY;

      x2 += velX2;
      y2 += velY2;

      checkBounds();

      repaint();
   }

   //check when "hitting the wall" and perform steps every hit
   public void checkBounds() {
      if (x > 350 || x < -220) {
         velX = -velX;
         changeColorI();
         ScaleI();
      }

      if (y > 300 || y < -50) {
         velY = -velY;
         changeColorI();
         ScaleI();
      }

      if (y2 > 20 || y2 < -330) {
         velY2 = -velY2;
         changeColorS();
         scaleS();
      }
      if (x2 > 360 || x2 < -220) {
         velX2 = -velX2;
         changeColorS();
         scaleS();
      }

   }

   //change I size
   public void ScaleI() {
      if (scaleX == 0.5) {
         scaleX = 1;
         scaleY = 1;
      } else {
         scaleY = 0.5;
         scaleX = 0.5;
      }

   }
   //change S size
   public void scaleS() {
      if (scaleX2 == 0.5) {
         scaleX2 = 1;
         scaleY2 = 1;
      } else {
         scaleY2 = 0.5;
         scaleX2 = 0.5;
      }
   }

   //pick random color
   public void changeColorI() {
      polIColor = new Color((int) (Math.random() * 255),
              (int) (Math.random() * 255), (int) (Math.random() * 255));
   }
   public void changeColorS() {
      polSColor = new Color((int) (Math.random() * 255),
              (int) (Math.random() * 255), (int) (Math.random() * 255));
   }


}