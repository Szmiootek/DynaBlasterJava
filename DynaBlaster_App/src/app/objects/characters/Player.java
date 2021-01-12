package app.objects.characters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * It is a class which represents Player object
 */
public class Player {
    BufferedImage image;
    int HP, startBombs, direction, hasBombs;
    double x, y, width, height;

    /**
     * Non-arg constructor of the Player class
     */
    public Player(){
        try {
            image = ImageIO.read(new File("DynaBlaster_App/resources/Player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method paints the player
     * @param g2d Graphics2D object
     * @param x position x where to paint the player
     * @param y position y where to paint the player
     * @param width width of the player
     * @param height height of the player
     */
    public void paint(Graphics2D g2d, double x, double y, double width, double height){
        g2d.drawImage(image, (int)Math.round(x), (int)Math.round(y), (int)Math.round(width), (int)Math.round(height),null);
    }

    /**
     * Method which moves the player
     * @param direction the direction where player will be moving
     */
    public void move(int direction){
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
     * Gets the x position of the player
     * @return x position
     */
    public double getX(){return x;}

    /**
     * Gets the y position of the player
     * @return y position
     */
    public double getY(){return y;}

    /**
     * Gets the width of the player
     * @return width
     */
    public double getWidth(){return width;}

    /**
     * Gets the height of the player
     * @return height
     */
    public double getHeight(){return height;}

    /**
     * Gets the direction
     * @return direction
     */
    public int getDirection(){return direction;}

    /**
     * Gets how many bombs does the player have
     * @return current number of bombs
     */
    public int getHasBombs(){return hasBombs;}

    /**
     * Gets player's HP
     * @return HP
     */
    public int getHP(){ return HP; }

    /**
     * Gets player's start amount of bombs
     * @return start amount of bombs
     */
    public int getStartBombs(){ return startBombs; }

    /**
     * Sets player's bombs
     * @param bombs start amount of bombs
     */
    public void setStartBombs(int bombs) {
        startBombs = bombs;
        hasBombs = startBombs;
    }

    /**
     * Sets player's pos_x
     * @param x x position
     */
    public void setX(double x) {this.x = x;}

    /**
     * Sets player's pos_y
     * @param y y position
     */
    public void setY(double y) {this.y = y;}

    /**
     * Sets player's width
     * @param width width
     */
    public void setWidth(double width) { this.width = width; }

    /**
     * Sets player's height
     * @param height height
     */
    public void setHeight(double height) { this.height = height; }

    /**
     * Sets player's HP
     * @param HP health points
     */
    public void setHP(int HP) {this.HP = HP;}

    /**
     * Sets player's current amount of bombs
     * @param hasBombs current amount of bombs
     */
    public void setHasBombs(int hasBombs) { this.hasBombs = hasBombs; }

    /**
     * Subtracts from the number of current bombs
     */
    public void shoot(){--hasBombs;}

    /**
     * Reaction for the damaging player
     */
    public void damaged(){--HP;}

    /**
     * Adds new health point
     */
    public void addHP(){++HP;}
}
