package app.objects.elements;

import javax.swing.*;
import java.awt.*;

/**
 * Bomb class
 */
public class Bomb {

    Image image;
    double pos_x, pos_y, width, height;
    int direction, movement;
    boolean players;

    /**
     * Parametrized constructor
     * @param pos_x start position x
     * @param pos_y start position y
     * @param direction direction of the bomb's movement
     * @param movement speed of the bomb's movement
     * @param players represents whether the bomb is player's or monster's
     */
    public Bomb(double pos_x, double pos_y, int direction, int movement, boolean players){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.direction = direction;
        this.movement = movement;
        image = new ImageIcon("DynaBlaster_App/resources/Bomb.png").getImage();
        this.players = players;
    }

    /**
     * This method paints the bomb
     * @param g2d Graphics2D object
     * @param x position x where to paint the bomb
     * @param y position y where to paint the bomb
     */
    public void paint(Graphics2D g2d, int x, int y){
        g2d.drawImage(image, x, y, (int)Math.round(width), (int)Math.round(height),null);
    }

    /**
     * Method which moves the bomb
     */
    public void move(){
        switch (direction) {
            case 37 -> --pos_x;
            case 38 -> --pos_y;
            case 39 -> ++pos_x;
            case 40 -> ++pos_y;
            default -> { }
        }
    }

    /**
     * Sets some graphic attributes of the Bomb object
     * @param width the width of the object
     * @param height the height of the object
     */
    public void setAttributes(double width, double height){
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the x position of the bomb
     * @return x position
     */
    public double getPos_x(){return pos_x;}

    /**
     * Gets the y position of the bomb
     * @return y position
     */
    public double getPos_y(){return pos_y;}

    /**
     * Gets the width of the bomb
     * @return width
     */
    public double getWidth(){return width;}

    /**
     * Gets the height of the bomb
     * @return height
     */
    public double getHeight(){return height;}

    /**
     * Gets the information whether the bomb is player's or not
     * @return true if player's bomb, false if not
     */
    public boolean getPlayers() {return players;}

}
