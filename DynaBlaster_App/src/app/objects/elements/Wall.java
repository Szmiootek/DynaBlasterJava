package app.objects.elements;

import javax.swing.*;
import java.awt.*;

/**
 * Wall class
 */
public class Wall{
    Image image;
    double x, y, width, height;

    /**
     * Non-args constructor
     */
    public Wall(){
        image = new ImageIcon("DynaBlaster_App/resources/Wall.jpg").getImage();
    }

    /**
     * This method paints the wall
     * @param g2d Graphics2D object
     * @param x position x where to paint the wall
     * @param y position y where to paint the wall
     * @param width width of the wall
     * @param height height of the wall
     */
    public void paint(Graphics2D g2d, double x, double y, double width, double height){
        g2d.drawImage(image, (int)Math.round(x), (int)Math.round(y), (int)Math.round(width), (int)Math.round(height), null);
    }
}

