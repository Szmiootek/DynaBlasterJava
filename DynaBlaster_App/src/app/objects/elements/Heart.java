package app.objects.elements;

import javax.swing.*;
import java.awt.*;

/**
 * Heart class
 */
public class Heart {
    Image image;
    double x, y, width, height;
    int id;
    static int counter = 0;

    /**
     * No-arg constructor
     */
    public Heart(){
        image = new ImageIcon("DynaBlaster_App/resources/Heart.png").getImage();
        id = counter;
        ++counter;
    }

    /**
     * This method paints the heart
     * @param g2d Graphics2D object
     * @param x position x where to paint the heart
     * @param y position y where to paint the heart
     * @param width width of the heart
     * @param height height of the heart
     */
    public void paint(Graphics2D g2d, double x, double y, double width, double height){
        g2d.drawImage(image, (int)Math.round(x), (int)Math.round(y), (int)Math.round(width), (int)Math.round(height), null);
    }
}
