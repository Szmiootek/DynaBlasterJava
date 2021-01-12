package app.objects.elements;

import javax.swing.*;
import java.awt.*;

/**
 * New level passage class
 */
public class new_Level {
    Image image;
    double x, y, width, height;

    /**
     * Non-args constructor
     */
    public new_Level(){
        image = new ImageIcon("DynaBlaster_App/resources/Hole.png").getImage();
    }

    /**
     * This method paints the new level object
     * @param g2d Graphics2D object
     * @param x position x where to paint the new level object
     * @param y position y where to paint the new level object
     * @param width width of the new level object
     * @param height height of the new level object
     */
    public void paint(Graphics2D g2d, double x, double y, double width, double height){
        g2d.drawImage(image, (int)Math.round(x), (int)Math.round(y), (int)Math.round(width), (int)Math.round(height), null);
    }
}
