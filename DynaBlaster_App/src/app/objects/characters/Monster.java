package app.objects.characters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Monster class
 */
public class Monster {
    BufferedImage image;
    int direction;
    double x, y, width, height;
    boolean alive;

    /**
     * Non-arg constructor of the Monster class
     */
    public Monster() {
        try {
            image = ImageIO.read(new File("DynaBlaster_App/resources/Monster.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        direction = (int)(Math.random() * ((KeyEvent.VK_DOWN - KeyEvent.VK_LEFT) + 1) + KeyEvent.VK_LEFT);
        alive = true;
    }

    /**
     * This method paints the monster
     * @param g2d Graphics2D object
     * @param x position x where to paint the monster
     * @param y position y where to paint the monster
     * @param width width of the monster
     * @param height height of the monster
     */
    public void paint(Graphics2D g2d, double x, double y, double width, double height){
        g2d.drawImage(image, (int)Math.round(x), (int)Math.round(y), (int)Math.round(width), (int)Math.round(height),null);
    }

    /**
     * Method which moves the monster
     * @param direction the direction where monster will be moving
     */
    public void move(int direction) {
        this.direction = direction;
        switch (direction) {
            case 37 -> --x;
            case 38 -> --y;
            case 39 -> ++x;
            case 40 -> ++y;
            default -> { }
        }
    }

    /**
     * Gets the x position of the monster
     * @return x position
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y position of the monster
     * @return y position
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the width of the monster
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of the monster
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets current direction of the monster's movement
     * @return direction as a KeyEvent number
     */
    public int getDirection() { return direction; }

    /**
     * Gets if the monster is alive
     * @return is alive?
     */
    public boolean getAlive() { return alive; }

    /**
     * Sets if the monster is alive
     * @param alive is alive or not
     */
    public void setAlive(boolean alive) { this.alive = alive; }

    /**
     * Sets x position of the monster
     * @param x x position
     */
    public void setX(double x) {this.x = x;}

    /**
     * Sets y position of the monster
     * @param y y position
     */
    public void setY(double y) {this.y = y;}

    /**
     * Sets monster's width
     * @param width width
     */
    public void setWidth(double width) { this.width = width; }

    /**
     * Sets monster's height
     * @param height height
     */
    public void setHeight(double height) { this.height = height; }
}
