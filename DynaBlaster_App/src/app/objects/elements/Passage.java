package app.objects.elements;

import javax.swing.*;
import java.awt.*;

/**
 * Passage class
 */
public class Passage {
    Image image;
    double x, y, width, height;

    /**
     * Non-args constructor
     */
    public Passage(){
        image = new ImageIcon("DynaBlaster_App/resources/Passage.png").getImage();
    }

    /**
     * This method paints the passage object
     * @param g2d Graphics2D object
     * @param x position x where to paint the passage object
     * @param y position y where to paint the passage object
     * @param width width of the passage object
     * @param height height of the passage object
     */
    public void paint(Graphics2D g2d, double x, double y, double width, double height){
        g2d.drawImage(image, (int)Math.round(x), (int)Math.round(y), (int)Math.round(width), (int)Math.round(height), null);
    }
}
