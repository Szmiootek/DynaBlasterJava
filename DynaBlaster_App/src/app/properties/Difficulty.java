package app.properties;

import app.main.Client;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Difficulty class - represents difficulty levels
 */

public class Difficulty {

    static int HP, bombs;

    /**
     * An instance of Difficulty
     */
    public static Difficulty _instance = null;

    /**
     * Difficulty level
     */
    public static Properties difficulties;

    /**
     * Non-args constructor
     */
    public Difficulty() { }

    /**
     * Method which allows to get an instance of the Difficulty class
     * @return _instance
     */
    public static Difficulty getInstance(){
        if(_instance == null)
            _instance = new Difficulty();
        return _instance;
    }

    /**
     * Method which loads difficulty configuration from file
     * @param difficulty_level difficulty level
     */
    public void load(int difficulty_level) {
        try {
            FileReader reader = new FileReader("DynaBlaster_App/src/config/Difficulties");
            difficulties = new Properties();
            difficulties.load(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No matching file found!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File exception has been thrown!");
        }

        switch (difficulty_level) {
            case 1 -> parseStrings("Easy");
            case 2 -> parseStrings("Medium");
            case 3 -> parseStrings("Hard");
            default -> { }
        }
    }

    /**
     * Method which loads difficulty configuration from server
     * @throws IOException IOException
     */
    public void loadFromServer() throws IOException {
        String response = Client.getDifficulties();
        int index = response.indexOf(';');
        HP = Integer.parseInt(response.substring(0, index));
        bombs = Integer.parseInt(response.substring(index + 1));
    }

    private void parseStrings(String level){
        String diff = difficulties.getProperty(level);
        int index = diff.indexOf(';');
        HP = Integer.parseInt(diff.substring(0, index));
        bombs = Integer.parseInt(diff.substring(index + 1));
    }
    /**
     * Gets number of initial HP of the player
     * @return HP
     */
    public int getHP(){ return HP; }

    /**
     * Gets number of initial bombs of the player
     * @return number of the bombs
     */
    public int getBombs() { return bombs; }
}
