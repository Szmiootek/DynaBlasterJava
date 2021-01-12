package app.objects.elements;

import javax.swing.*;
import java.awt.*;

/**
 * Coin class
 */
public class Coin{
    Image image;
    double x, y, width, height;
    int id;
    static int counter = 0;

    /**
     * No-arg constructor
     */
    public Coin(){
        image = new ImageIcon("DynaBlaster_App/resources/Coin.png").getImage();
        id = counter;
        ++counter;
    }

    /**
     * This method paints the coin
     * @param g2d Graphics2D object
     * @param x position x where to paint the coin
     * @param y position y where to paint the coin
     * @param width width of the coin
     * @param height height of the coin
     */
    public void paint(Graphics2D g2d, double x, double y, double width, double height){
        g2d.drawImage(image, (int)Math.round(x), (int)Math.round(y), (int)Math.round(width), (int)Math.round(height), null);
    }
}
